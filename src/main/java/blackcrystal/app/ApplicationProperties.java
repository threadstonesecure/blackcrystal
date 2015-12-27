package blackcrystal.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ConfigurationProperties(prefix = "blackcrystal")
public class ApplicationProperties {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String workspace;

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

}


