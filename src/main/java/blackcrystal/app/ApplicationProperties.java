package blackcrystal.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ConfigurationProperties(prefix = "blackcrystal")
public class ApplicationProperties {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String workspace;

    @Value("${blackcrystal.elasticsearch.port}")
    private String elasticSearchPort;

    @Value("${blackcrystal.elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${blackcrystal.elasticsearch.index}")
    private String elasticSearchIndex;


    @Value("${server.port}")
    private String serverPort;


    private String serverAddress;

    public String getWorkspace() {
        return workspace;
    }

    public Path jobsDirectory() {
        return Paths.get(this.workspace, "jobs");
    }

    public Path resourcesDirectory() {
        return Paths.get(this.workspace, "resources");
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }


    public String getElasticSearchHost() {
        return elasticSearchHost;
    }

    public String getElasticSearchPort() {
        return elasticSearchPort;
    }

    public String getElasticSearchIndex() {
        return elasticSearchIndex;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public ApplicationProperties() {
        try{
           setServerAddress(InetAddress.getLocalHost().getHostAddress());
        }catch (Exception e){
            logger.error("Could not get the host address",e);
        }
    }
}


