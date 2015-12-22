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
import java.util.Optional;

@Component("executionService")
public class ExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    @Autowired
    private ApplicationProperties properties;


    public Optional<JobExecutionInfo> getExecutionInfo(String name) {
        Path path = getJobExecutionInfo(name);
        if (Files.exists(path)) {
            try {
                return Optional.of(FileUtility.read(path, JobExecutionInfo.class));
            } catch (Exception e) {
                logger.error("could not read the execution file ", e);
            }
        }
        return Optional.empty();
    }

    //TODO : Remove duplicated codes between services
    private Path getJobDir(String name) {
        return properties.jobsDirectory().resolve(name);
    }


    private Path getJobExecutionInfo(String name) {
        return getJobDir(name).resolve("execution.json");
    }




}