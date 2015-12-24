package blackcrystal.it

import blackcrystal.Application
import blackcrystal.model.JobExecutionInfo
import blackcrystal.model.JobExecutionResult
import blackcrystal.utility.TestUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
@WebAppConfiguration
@IntegrationTest('server.port:0')
class ExecutionControllerSpec extends Specification {


    Logger logger = LoggerFactory.getLogger(ExecutionControllerSpec.class);

    @Value('${local.server.port}')
    int port;

    //TODO : Clean up used variables - do not duplicate
    TestUtils testUtils = new TestUtils()

    String jobId = "/TestJob1"

    String getBasePath() { "job" }

    URI serviceURI(String path = "") {
        new URI("http://localhost:$port/${basePath}${path}")
    }


    void "/job/{name}/executions should return all executions"() {
        given:
        def expectedJExecutionInfo = testUtils.executions
        when:
        def request = RequestEntity.get(serviceURI("$jobId/executions")).build()
        def response = new RestTemplate().exchange(request, JobExecutionInfo)
        then:
        response.body == expectedJExecutionInfo
        response.statusCode == HttpStatus.OK
    }


    void "/job/{name}/execution/{executionId} should return execution result"() {
        given:
        def expectedExecutionResult = testUtils.executionResult
        when:
        def request = RequestEntity.get(serviceURI("$jobId/execution/30")).build()
        def response = new RestTemplate().exchange(request, JobExecutionResult)
        then:
        response.body == expectedExecutionResult
        response.statusCode == HttpStatus.OK
    }

    void "/job/{name}/execution/{executionId} 404 - NOT_FOUND if execution does not exist"() {
        when:
        def request = RequestEntity.get(serviceURI("$jobId/execution/1000")).build()
        new RestTemplate().exchange(request, JobExecutionResult)
        then:
        HttpClientErrorException exception = thrown()
        exception.statusCode == HttpStatus.NOT_FOUND
    }

    void "/job/{name}/execution/{executionId}/log should return execution log"() {
        when:
        def request = RequestEntity.get(serviceURI("$jobId/execution/30/log")).build()
        def response = new RestTemplate().exchange(request, String)
        then:
        response.body.contains("This is sample output...\nThis is sample output...") == true
        response.statusCode == HttpStatus.OK
    }

    void "/job/{name}/execution/{executionId}/log should return 404 - NOT_FOUND if execution does not exist"() {
        when:
        def request = RequestEntity.get(serviceURI("$jobId/execution/1000/log")).build()
        new RestTemplate().exchange(request, String)
        then:
        HttpClientErrorException exception = thrown()
        exception.statusCode == HttpStatus.NOT_FOUND
    }

}

