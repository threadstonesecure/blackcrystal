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

    public boolean writeExecutionInfo(JobConfig jobConfig, JobExecutionInfo info) {
        Path path = getJobExecutionInfo(jobConfig.name);
        return FileUtility.write(path, info);
    }

    //TODO - correct naming
    public boolean writeExecutionResult(JobConfig jobConfig, JobExecutionResult result, Integer buildid) {
        Path path = getJobResultPath(jobConfig.name, buildid);
        return FileUtility.write(path, result);
    }

    public boolean writeJobConfig(JobConfig jobConfig) {
        Path jobConfigFile = getJobConfFile(jobConfig.name);
        Path jobDirectory = getJobDirPath(jobConfig.name);
        FileUtility.createDirectory(jobDirectory);
        return FileUtility.write(jobConfigFile, jobConfig);
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


    public Integer getNextExecId(JobConfig jobConfig) {
        Path path = getJobExecutionInfo(jobConfig.name);
        JobExecutionInfo executionInfo = null;
        if (Files.exists(path)) {
            try {
                executionInfo = FileUtility.getJobExecutionConfig(path.toString());
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

    private Path getJobsDir() {
        return Paths.get(properties.getJobsDirectory());
    }

    private String getJobDir(String name) {
        return properties.getJobsDirectory() + "/" + name;
    }

    private Path getJobDirPath(String name) {
        return Paths.get(getJobDir(name));
    }

    public Path getJobBuildDirPath(String name, Integer jobid) {
        return Paths.get(getJobDir(name) + "/" + jobid);
    }

    private Path getJobConfFile(String name) {
        return Paths.get(properties.getJobsDirectory() + "/" + name + "/config.json");
    }

    private Path getJobExecutionInfo(String name) {
        return Paths.get(properties.getJobsDirectory() + "/" + name + "/execution.json");
    }

    private Path getJobResultPath(String name, Integer buidlid) {
        return Paths.get(properties.getJobsDirectory() + "/" + name + "/" + buidlid + "/result.json");
    }


}
