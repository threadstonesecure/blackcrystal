package blackcrystal.service;

import blackcrystal.model.JobConfig;
import blackcrystal.utility.FileUtility;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component("jobConfigService")
public class JobConfigService {

    private static final Logger logger = LoggerFactory.getLogger(JobConfigService.class);


    @Autowired
    private DirectoryService directoryService;

    public List<JobConfig> loadJobs() {
        List<JobConfig> jobs = new ArrayList<>();

        List<Path> directories = FileUtility.getSubDirectories(directoryService.jobsDirectory());
        for (Path p : directories) {
            try {
                jobs.add(FileUtility.read(p.resolve("config.json"), JobConfig.class));
            } catch (Exception e) {
                logger.error("Could not load the jobs:" + p.toString(), e);
            }
        }
        return jobs;
    }


    public boolean writeJobConfig(JobConfig jobConfig) {
        Path jobConfigFile = directoryService.configFile(jobConfig.name);
        Path jobDirectory = directoryService.jobDirectory(jobConfig.name);
        FileUtility.createDirectory(jobDirectory);
        return FileUtility.write(jobConfigFile, jobConfig);
    }

    public boolean delete(String name) {
        try {
            FileUtils.deleteDirectory((new File(directoryService.jobDirectory(name).toString())));
            return true;
        } catch (Exception e) {
            logger.debug("Could not delete the job config file : " + name, e);
            return false;
        }
    }

    public boolean jobExist(JobConfig jobConfig) {
        return Files.exists(directoryService.jobDirectory(jobConfig.name));
    }

}