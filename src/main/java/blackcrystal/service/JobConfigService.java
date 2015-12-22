package blackcrystal.service;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.model.JobConfig;
import blackcrystal.model.JobExecutionInfo;
import blackcrystal.model.JobExecutionResult;
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
    private ApplicationProperties properties;

    public List<JobConfig> loadJobs() {
        List<JobConfig> jobs = new ArrayList<>();

        List<Path> directories = FileUtility.getSubDirectories(properties.jobsDirectory());
        for (Path p : directories) {
            try {
                jobs.add(FileUtility.read(p.resolve("config.json"), JobConfig.class));
            } catch (Exception e) {
                logger.error("Could not load the jobs:" + p.toString(), e);
            }
        }
        return jobs;
    }

    public boolean writeExecutionInfo(JobConfig jobConfig, JobExecutionInfo info) {
        Path path = getJobExecutionInfo(jobConfig.name);
        return FileUtility.write(path, info);
    }

    //TODO - correct naming
    public boolean writeExecutionResult(JobConfig jobConfig, JobExecutionResult result, String executionId) {
        Path path = getJobResultPath(jobConfig.name, executionId);
        return FileUtility.write(path, result);
    }

    public boolean writeJobConfig(JobConfig jobConfig) {
        Path jobConfigFile = getJobConfFile(jobConfig.name);
        Path jobDirectory = getJobDir(jobConfig.name);
        FileUtility.createDirectory(jobDirectory);
        return FileUtility.write(jobConfigFile, jobConfig);
    }

    public boolean delete(String name) {
        try {
            FileUtils.deleteDirectory((new File(getJobDir(name).toString())));
            return true;
        } catch (Exception e) {
            logger.debug("Could not delete the job config file : " + name, e);
            return false;
        }
    }

    public boolean jobExist(JobConfig jobConfig) {
        return Files.exists(getJobConfFile(jobConfig.name));
    }


    public Integer getNextExecId(JobConfig jobConfig) {
        Path path = getJobExecutionInfo(jobConfig.name);
        JobExecutionInfo executionInfo = null;
        if (Files.exists(path)) {
            try {
                executionInfo = FileUtility.read(path, JobExecutionInfo.class);
            } catch (Exception e) {
                logger.error("could not read the execution file ", e);
            }
        } else {
            executionInfo = new JobExecutionInfo(0, new ArrayList<>());
        }

        Integer executionId = executionInfo.lastExecutionId + 1;
        executionInfo.executions.add(executionId);
        executionInfo.lastExecutionId = executionId;
        this.writeExecutionInfo(jobConfig, executionInfo);
        return executionId;
    }


    public Path getJobBuildDirPath(String name, String executionId) {
        return getJobDir(name).resolve(executionId);
    }


    private Path getJobDir(String name) {
        return properties.jobsDirectory().resolve(name);
    }

    private Path getJobConfFile(String name) {
        return getJobDir(name).resolve("config.json");
    }

    private Path getJobExecutionInfo(String name) {
        return getJobDir(name).resolve("execution.json");
    }


    private Path getJobResultPath(String name, String executionId) {
        return getJobBuildDirPath(name, executionId).resolve("result.json");
    }


}
