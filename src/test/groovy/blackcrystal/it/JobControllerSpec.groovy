package blackcrystal.it

import blackcrystal.Application
import blackcrystal.model.JobConfig
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
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
@WebAppConfiguration
@IntegrationTest('server.port:0')
class JobControllerSpec extends Specification {


    Logger logger = LoggerFactory.getLogger(Specification.class);

    @Value('${local.server.port}')
    int port;


    TestUtils testUtils = new TestUtils()

    String jobId = "TestJob1"

    String getBasePath() { "job/" }

    URI serviceURI(String path = "") {
        new URI("http://localhost:$port/${basePath}${path}")
    }

    void "/jobs should return all available job configuraiton"() {
        when:
        def request = RequestEntity.get(new URI("http://localhost:$port/jobs")).build()
        def response = new RestTemplate().exchange(request, List)
        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 2
        response.body.each { assert it.name.contains("TestJob") }
    }

    void "/job/{jobID} should return the job config"() {
        given:
        def expectedJobConfig = testUtils.firstJobConfig
        when:
        def request = RequestEntity.get(serviceURI(jobId)).build()
        def response = new RestTemplate().exchange(request, JobConfig)
        then:
        response.body == expectedJobConfig
        response.statusCode == HttpStatus.OK
    }

    void "/job/{jobID} if job id is wrong it should return 404 - NOT_FOUND"() {
        when:
        def request = RequestEntity.get(serviceURI("wrong_job_id")).build()
        def response = new RestTemplate().exchange(request, JobConfig)
        then:
        HttpClientErrorException exception = thrown()
        exception.statusCode == HttpStatus.NOT_FOUND
    }


    void "posting job should return NOT_IMPLEMENTED"() {
        given:
        RequestEntity request = RequestEntity.post(serviceURI(jobId)).build()
        when:
        new RestTemplate().exchange(request, Object)
        then:
        thrown HttpServerErrorException
    }

    void "putting job should return NOT_IMPLEMENTED"() {
        given:
        RequestEntity request = RequestEntity.put(serviceURI(jobId)).build()
        when:
        new RestTemplate().exchange(request, Object)
        then:
        thrown HttpServerErrorException
    }


    void "deleting job should return NOT_IMPLEMENTED"() {
        given:
        RequestEntity request = RequestEntity.delete(serviceURI(jobId)).build()
        when:
        new RestTemplate().exchange(request, Object)
        then:
        thrown HttpServerErrorException
    }


    void "triggering run on job should return NOT_IMPLEMENTED"() {
        when:
        new RestTemplate().getForEntity(serviceURI("$jobId/run"), String.class)
        then:
        thrown HttpServerErrorException
    }

    void "cancelling job should return NOT_IMPLEMENTED"() {
        when:
        new RestTemplate().getForEntity(serviceURI("$jobId/cancel"), String.class)
        then:
        thrown HttpServerErrorException
    }

    void "reloading job should return NOT_IMPLEMENTED"() {
        when:
        new RestTemplate().getForEntity(serviceURI("$jobId/reload"), String.class)
        then:
        thrown HttpServerErrorException
    }

}

