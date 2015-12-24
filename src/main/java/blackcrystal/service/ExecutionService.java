package blackcrystal.service;

import blackcrystal.model.JobConfig;
import blackcrystal.model.JobExecutionInfo;
import blackcrystal.model.JobExecutionResult;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

@Component("executionService")
public class ExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    @Autowired
    private DirectoryService directoryService;


    public Optional<JobExecutionInfo> getExecutionInfo(String name) {
        Path path = directoryService.executionFile(name);
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
        Path path = directoryService.executionResultFile(name, executionId.toString());
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
        Path path = directoryService.executionLog(name, executionId.toString());
        if (Files.exists(path)) {
            return Optional.of(path);
        }
        return Optional.empty();
    }


    public Integer getNextExecId(JobConfig jobConfig) {
        Path path = directoryService.executionFile(jobConfig.name);
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

    public boolean writeExecutionInfo(JobConfig jobConfig, JobExecutionInfo info) {
        Path path = directoryService.executionFile(jobConfig.name);
        return FileUtility.write(path, info);
    }


}
