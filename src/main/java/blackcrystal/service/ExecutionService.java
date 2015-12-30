package blackcrystal.service;

import blackcrystal.model.JobConfig;
import blackcrystal.model.JobExecution;
import blackcrystal.model.PageAssembler;
import blackcrystal.repository.ExecutionRepository;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Transactional
@Component("executionService")
public class ExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    @Autowired
    private ExecutionRepository executionRepository;


    @Autowired
    private DirectoryService directoryService;

    public Optional<Path> getExecutionLogPath(String name, Integer executionId) {
        Path path = directoryService.executionLog(name, executionId.toString());
        if (Files.exists(path)) {
            return Optional.of(path);
        }
        return Optional.empty();
    }

    public Integer getNextExecId(JobConfig jobConfig) {
        Integer lastExecutionId = executionRepository.findLastExecutionID(jobConfig.name);
        return lastExecutionId + 1;
    }

    public JobExecution writeExecutionResult(JobConfig jobConfig, JobExecution jobExecution) {
        Path path = directoryService.executionResultFile(jobConfig.name, jobExecution.getExecutionId().toString());
        FileUtility.write(path, jobExecution);
        return executionRepository.save(jobExecution);
    }

    public Optional<JobExecution> getExecution(String jobName, Integer executionId) {
        JobExecution jobExecution = executionRepository.findByJob(jobName, executionId);
        if (jobExecution != null) {
            return Optional.of(jobExecution);
        } else {
            return Optional.empty();
        }
    }

    public Optional<PageAssembler<?>> getExecutions(String name, int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "executionId");
        Page<JobExecution> executions = executionRepository.findByJob(name, pageable);
        if (executions.hasContent()) {
            return Optional.of(new PageAssembler<>(executions));
        } else {
            return Optional.empty();
        }

    }
}
