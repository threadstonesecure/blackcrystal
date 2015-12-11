package blackcrystal.it

import blackcrystal.Application
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
@WebAppConfiguration
@IntegrationTest('server.port:0')
class ControllerSpec extends Specification {


    @Value('${local.server.port}')
    int port;

    void "should return main page information"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:$port", String.class)
        then:
        entity.statusCode == HttpStatus.OK
        entity.body == 'In need of an UI!'
    }


    void "should return health information"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:$port" + "/health", String.class)
        then:
        entity.statusCode == HttpStatus.OK
    }


}
