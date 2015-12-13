package blackcrystal.service;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.model.JobConfig;
import blackcrystal.utility.FileUtility;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component("jobConfigService")
public class JobConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    @Autowired
    private ApplicationProperties properties;

    public List<JobConfig> loadJobs() {
        List<JobConfig> jobs = new ArrayList<>();

        List<Path> directories = FileUtility.getSubDirectories(getJobsDir());
        for (Path p : directories) {
            try {
                jobs.add(FileUtility.getJobConfig(p.toString() + "/config.json"));
            } catch (Exception e) {
                logger.error("Could not load the jobs:" + p.toString(), e);
            }
        }
        return jobs;
    }

    public boolean write(JobConfig jobConfig) {
        Path jobConfigFile = getJobConfFile(jobConfig.name);
        String asJson = new Gson().toJson(jobConfig);
        Path jobDirectory = getJobDirPath(jobConfig.name);
        try {
            if (!Files.exists(jobDirectory)) {
                Files.createDirectory(jobDirectory);
            }
            Files.write(jobConfigFile, asJson.getBytes());
            return true;
        } catch (Exception e) {
            logger.debug("Could not write the job config file : " + jobConfig.name, e);
            return false;
        }
    }

    public boolean delete(String name) {
        try {
            FileUtils.deleteDirectory((new File(getJobDir(name))));
            return true;
        } catch (Exception e) {
            logger.debug("Could not delete the job config file : " + name, e);
            return false;
        }
    }

    public boolean jobExist(JobConfig jobConfig) {
        return Files.exists(getJobConfFile(jobConfig.name));
    }


    private Path getJobsDir() {
        return Paths.get(properties.getWorkspace() + "/jobs");
    }

    private String getJobDir(String name) {
        return properties.getWorkspace() + "/jobs/" + name;
    }

    private Path getJobDirPath(String name) {
        return Paths.get(getJobDir(name));
    }

    private Path getJobConfFile(String name) {
        return Paths.get(properties.getWorkspace() + "/jobs/" + name + "/config.json");
    }


}
