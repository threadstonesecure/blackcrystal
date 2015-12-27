package blackcrystal.it

import blackcrystal.Application
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
class ResourceControllerSpec extends Specification {


    Logger logger = LoggerFactory.getLogger(ResourceControllerSpec.class);

    @Value('${local.server.port}')
    int port;

    String resourceName = "/ResourceName"

    String getBasePath() { "resource" }

    URI serviceURI(String path = "") {
        new URI("http://localhost:$port/${basePath}${path}")
    }

    void "/resources should return NOT_IMPLEMENTED"() {
        when:
        def request = RequestEntity.get(new URI("http://localhost:$port/resources")).build()
        def response = new RestTemplate().exchange(request, List)
        then:
        response.statusCode == HttpStatus.OK
        response.body.each { assert it.name.contains("TestResource") }
    }

    void "/resource/:name should return NOT_IMPLEMENTED for delete request"() {
        given:
        RequestEntity request = RequestEntity.delete(serviceURI(resourceName)).build()
        when:
        new RestTemplate().exchange(request, String)
        then:
        thrown HttpServerErrorException
    }

    void "/resource/:name should return NOT_IMPLEMENTED for get request"() {
        given:
        RequestEntity request = RequestEntity.get(serviceURI(resourceName)).build()
        when:
        new RestTemplate().exchange(request, String)
        then:
        thrown HttpServerErrorException
    }

    void "/resource/:name should return NOT_IMPLEMENTED for put request"() {
        given:
        RequestEntity request = RequestEntity.put(serviceURI("")).build()
        when:
        new RestTemplate().exchange(request, String)
        then:
        thrown HttpServerErrorException
    }


}

