package blackcrystal.app;


import blackcrystal.model.JobConfig;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "blackcrystal")
public class ApplicationProperties {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String workspace;

    private List<JobConfig> jobs;

    public String getWorkspace() {
        return workspace;
    }

    public List<JobConfig> getJobs() {
        return jobs;
    }


    public void setWorkspace(String workspace) {
        this.workspace = workspace;
        this.jobs = loadJobs();
    }

    private List<JobConfig> loadJobs() {
        List<JobConfig> jobs = new ArrayList<>();
        List<Path> directories = FileUtility.getSubDirectories(getWorkspace() + "/jobs/");

        for (Path p : directories) {
            try {
                jobs.add(FileUtility.getJobConfig(p.toString() + "/config.json"));
            } catch (Exception e) {
                logger.error("Could not load the jobs:", e);
            }
        }
        return jobs;
    }


}


