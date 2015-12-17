package blackcrystal.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "blackcrystal")
public class ApplicationProperties {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String workspace;

    public String getWorkspace() {
        return workspace;
    }

    public String getJobsDirectory() {
        return this.workspace+"/jobs";
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

}


