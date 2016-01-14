package blackcrystal.it

import blackcrystal.Application
import blackcrystal.model.JobConfig
import blackcrystal.model.Resource
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
class ResourceControllerSpec extends Specification {

    TestUtils testUtils = new TestUtils()


    Logger logger = LoggerFactory.getLogger(ResourceControllerSpec.class);

    @Value('${local.server.port}')
    int port;

    String resourceName = "/ResourceName"

    String getBasePath() { "resource" }

    URI serviceURI(String path = "") {
        new URI("http://localhost:$port/${basePath}${path}")
    }

    void "/resources should return list of created resources"() {
        when:
        def request = RequestEntity.get(new URI("http://localhost:$port/resources")).build()
        def response = new RestTemplate().exchange(request, List)
        then:
        response.statusCode == HttpStatus.OK
        response.body.each { assert it.name.contains("TestResource") }
    }


    void "/resource-types should returns all supported resource types"() {
        when:
        def request = RequestEntity.get(new URI("http://localhost:$port/resource-types")).build()
        def response = new RestTemplate().exchange(request, String)
        then:
        response.statusCode == HttpStatus.OK
        response.body == "[\"git\",\"directory\"]"
    }

    void "/resource/:name should return NOT_IMPLEMENTED for delete request"() {
        given:
        RequestEntity request = RequestEntity.delete(serviceURI(resourceName)).build()
        when:
        new RestTemplate().exchange(request, String)
        then:
        thrown HttpServerErrorException
    }

    void "/resource/:name should return the resource"() {
        given:
        def expectedResourceConfig = testUtils.resourceConfig2
        when:
        def request = RequestEntity.get(serviceURI("/$expectedResourceConfig.name")).build()
        def response = new RestTemplate().exchange(request, String)
        then:
        response.body.contains(expectedResourceConfig.name) == true
    }

    void "/resource/:name should return return 404 - NOT_FOUND, if resource does not exist"() {
        when:
        def request = RequestEntity.get(serviceURI("/WrongResourceName")).build()
        new RestTemplate().exchange(request, String)
        then:
        then:
        HttpClientErrorException exception = thrown()
        exception.statusCode == HttpStatus.NOT_FOUND
    }

    void "/resource should return CREATED for PUT request if resource does not exist"() {
        given:
        def config = testUtils.resourceConfig
        when:
        def request = RequestEntity.put(serviceURI()).body(config)
        def response = new RestTemplate().exchange(request, Resource)
        then:
        response.statusCode == HttpStatus.CREATED
    }

    void "/resource should return CREATED for OK request if resource exist"() {
        given:
        def config = testUtils.resourceConfig2
        when:
        def request = RequestEntity.put(serviceURI()).body(config)
        def response = new RestTemplate().exchange(request, Resource)
        then:
        response.statusCode == HttpStatus.OK
    }


}

