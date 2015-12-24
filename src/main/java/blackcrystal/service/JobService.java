package blackcrystal.service;

import blackcrystal.model.JobConfig;
import blackcrystal.model.JobExecutionInfo;
import blackcrystal.model.JobExecutionResult;
import blackcrystal.runner.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("jobService")
public class JobService {

    private List<JobConfig> jobs;

    @Autowired
    private JobConfigService jobConfigService;


    @Autowired
    private ExecutionService executionService;


    @Autowired
    private JobScheduler scheduledTasks;

    public Optional<JobConfig> get(String name) {
        return this.getJobs().stream()
                .filter(j -> j.name.equals(name))
                .findFirst();
    }

    public Optional<JobExecutionInfo> getExecutions(String name) {
        return executionService.getExecutionInfo(name);
    }

    public Optional<JobExecutionResult> getExecutionResult(String name, Integer executionId) {
        return executionService.getExecutionResult(name, executionId);
    }

    public Optional<Path> getExecutionLogPath(String name, Integer executionId) {
        return executionService.getExecutionLogPath(name, executionId);
    }


    public List<JobConfig> getJobs() {
        if (jobs == null) {
            jobs = jobConfigService.loadJobs();
        }
        return jobs;
    }

    public Optional<JobConfig> update(JobConfig jobConfig) {
        if (jobConfigService.writeJobConfig(jobConfig)) {
            jobs = jobs.stream()
                    .map(j -> j.name.equals(jobConfig.name) ? jobConfig : j)
                    .collect(Collectors.toList());
            scheduledTasks.updateJob(jobConfig);
            return get(jobConfig.name);
        } else {
            return Optional.empty();
        }
    }

    public Optional<JobConfig> create(JobConfig jobConfig) {
        if (jobConfigService.writeJobConfig(jobConfig)) {
            jobs.add(jobConfig);
            scheduledTasks.addJob(jobConfig);
            return get(jobConfig.name);
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(String name) {
        if (jobConfigService.delete(name)) {
            jobs = jobs.stream()
                    .filter(j -> !j.name.equals(name))
                    .collect(Collectors.toList());
            scheduledTasks.deleteJob(name);
            return true;
        } else {
            return false;
        }
    }

    public boolean jobExist(String name) {
        JobConfig j = new JobConfig();
        j.name = name;
        return jobConfigService.jobExist(j);
    }

    public boolean jobExist(JobConfig jobConfig) {
        return jobConfigService.jobExist(jobConfig);
    }

}
