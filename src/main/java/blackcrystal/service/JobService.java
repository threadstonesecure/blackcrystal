package blackcrystal.service;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.model.JobConfig;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("jobService")
@Transactional
public class JobService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);


    private List<JobConfig> jobs;

    @Autowired
    private ApplicationProperties properties;

    public Optional<JobConfig> getJob(String name) {
        return this.getJobs().stream()
                .filter(j -> j.name.equals(name))
                .findFirst();
    }


    public List<JobConfig> getJobs() {
        if (jobs == null) {
            jobs = loadJobs();
        }
        return jobs;
    }


    private List<JobConfig> loadJobs() {
        List<JobConfig> jobs = new ArrayList<>();
        List<Path> directories = FileUtility.getSubDirectories(properties.getWorkspace() + "/jobs/");

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
