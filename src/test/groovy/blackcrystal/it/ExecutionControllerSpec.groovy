package blackcrystal.it

import blackcrystal.Application
import blackcrystal.model.JobExecutionInfo
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
import org.springframework.web.client.HttpServerErrorException
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
        response.body.executions.size() == expectedJExecutionInfo.executions.size()
        response.body.lastExecutionId == expectedJExecutionInfo.lastExecutionId
        response.statusCode == HttpStatus.OK
    }


    void "/job/{name}/execution/{executionId}  should return NOT_IMPLEMENTED"() {
        when:
        new RestTemplate().getForEntity(serviceURI("$jobId/execution/30"), String.class)
        then:
        thrown HttpServerErrorException
    }

    void "/job/{name}/execution/{executionId}/log should return NOT_IMPLEMENTED"() {
        when:
        new RestTemplate().getForEntity(serviceURI("$jobId/execution/30/log"), String.class)
        then:
        thrown HttpServerErrorException
    }

}

