package blackcrystal.it

import blackcrystal.Application
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.RequestEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
@WebAppConfiguration
@IntegrationTest('server.port:0')
class JobControllerSpec extends Specification {


    @Value('${local.server.port}')
    int port;

    String jobId = "MyJob"
    String getBasePath() { "job/" }

    URI serviceURI(String path = "") {
        new URI("http://localhost:$port/${basePath}${path}")
    }

    void "fetching all jobs should return NOT_IMPLEMENTED"() {
        when:
        new RestTemplate().getForEntity("http://localhost:$port/jobs", String.class)
        then:
        thrown HttpServerErrorException
    }

    void "fetching a job should return NOT_IMPLEMENTED"() {
        given:
        RequestEntity request = RequestEntity.get(serviceURI(jobId)).build()
        when:
        new RestTemplate().exchange(request, Object)
        then:
        thrown HttpServerErrorException
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

