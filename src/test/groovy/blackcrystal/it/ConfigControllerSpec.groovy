package blackcrystal.it

import blackcrystal.Application
import blackcrystal.app.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
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
class ConfigControllerSpec extends Specification {

    @Autowired
    private ApplicationProperties properties;

    @Value('${local.server.port}')
    int port;

    void "should return application config correctly"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:$port/config", ApplicationProperties)
        then:
        entity.statusCode == HttpStatus.OK
        entity.body.elasticSearchHost == properties.elasticSearchHost
        entity.body.elasticSearchPort == properties.elasticSearchPort
        entity.body.workspace == properties.workspace
    }


}
