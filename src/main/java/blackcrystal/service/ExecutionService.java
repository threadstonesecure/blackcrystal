package blackcrystal.service;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.model.JobExecutionInfo;
import blackcrystal.model.JobExecutionResult;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
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

    public Optional<JobExecutionResult> getExecutionResult(String name, Integer executionId) {
        Path path = getJobResultPath(name, executionId.toString());
        if (Files.exists(path)) {
            try {
                return Optional.of(FileUtility.read(path, JobExecutionResult.class));
            } catch (Exception e) {
                logger.error("could not read the execution file ", e);
            }
        }
        return Optional.empty();
    }

    public Optional<Path> getExecutionLogPath(String name, Integer executionId) {
        Path path = getJobBuildDirPath(name, executionId.toString()).resolve("log");
        if (Files.exists(path)) {
            return Optional.of(path);
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

    public Path getJobBuildDirPath(String name, String executionId) {
        return getJobDir(name).resolve(executionId);
    }


    private Path getJobResultPath(String name, String executionId) {
        return getJobBuildDirPath(name, executionId).resolve("result.json");
    }

}
