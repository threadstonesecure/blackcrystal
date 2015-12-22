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
import spock.lang.Stepwise

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
@WebAppConfiguration
@IntegrationTest('server.port:0')
@Stepwise
class JobControllerSpec extends Specification {


    Logger logger = LoggerFactory.getLogger(JobControllerSpec.class);

    @Value('${local.server.port}')
    int port;


    TestUtils testUtils = new TestUtils()

    String jobId = "/TestJob1"

    String getBasePath() { "job" }

    URI serviceURI(String path = "") {
        new URI("http://localhost:$port/${basePath}${path}")
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


    void "/job/{jobID} on PUT method if exist should return 200 - OK and update resource"() {
        given:
        def previosConfig = testUtils.firstJobConfig
        def newConfig = testUtils.firstJobConfig
        newConfig.command = "newCommand"
        when:
        def request = RequestEntity.put(serviceURI()).body(newConfig)
        def response = new RestTemplate().exchange(request, JobConfig)
        then:
        response.body.command != previosConfig.command
        response.body == newConfig
        response.statusCode == HttpStatus.OK
    }

    void "/job/{jobID} on PUT method if does not exist should return 201 - CREATED and add resource"() {
        given:
        def expectedConfig = testUtils.thirdTestConfig
        when:
        def request = RequestEntity.put(serviceURI()).body(expectedConfig)
        def response = new RestTemplate().exchange(request, JobConfig)
        then:
        response.body == expectedConfig
        response.statusCode == HttpStatus.CREATED
    }

    void "/job/{jobID} on DELETE method, if resource exist, should return 202 - ACCEPTED"() {
        given:
        RequestEntity request = RequestEntity.delete(serviceURI("/" + testUtils.thirdTestConfig.name)).build()
        when:
        def response = new RestTemplate().exchange(request, JobConfig)
        then:
        response.statusCode == HttpStatus.ACCEPTED
    }

    void "/job/{jobID} on DELETE method, if resource does not exist, should return 404 - NOT_FOUND"() {
        given:
        RequestEntity request = RequestEntity.delete(serviceURI("/wrong_job_id")).build()
        when:
        new RestTemplate().exchange(request, JobConfig)
        then:
        HttpClientErrorException exception = thrown()
        exception.statusCode == HttpStatus.NOT_FOUND
    }

    void "/jobs should return all available job configuration"() {
        when:
        def request = RequestEntity.get(new URI("http://localhost:$port/jobs")).build()
        def response = new RestTemplate().exchange(request, List)
        then:
        response.statusCode == HttpStatus.OK
        response.body.each { assert it.name.contains("TestJob") }
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

