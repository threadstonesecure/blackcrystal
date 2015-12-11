package blackcrystal.it

import blackcrystal.Application
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.ResponseEntity
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

    void "fetching job should return NOT_IMPLEMENTED"() {
        when:
            new RestTemplate().getForEntity("http://localhost:$port/job/jobname", String.class)
        then:
            thrown HttpServerErrorException
    }

    void "triggering run on job should return NOT_IMPLEMENTED"() {
        when:
            new RestTemplate().getForEntity("http://localhost:$port/job/jobname/run", String.class)
        then:
            thrown HttpServerErrorException
    }

    void "cancelling running job should return NOT_IMPLEMENTED"() {
        when:
            new RestTemplate().getForEntity("http://localhost:$port/job/jobname/cancel", String.class)
        then:
            thrown HttpServerErrorException
    }


}

