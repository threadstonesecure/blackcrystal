package blackcrystal.runner;

import blackcrystal.model.JobConfig;
import blackcrystal.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private TaskExecutor taskExecutor;


    @Autowired
    private ThreadPoolTaskScheduler scheduler;


    @Autowired
    private JobService jobService;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        return scheduler;
    }

    private List<ScheduledFuture> scheduledFutures = new ArrayList<>();


    //TODO get pool size from application config
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(50);
        return executor;
    }


    public boolean addJob(JobConfig jobConfig) {
        //TODO : TOBE IMPLEMENT
        return true;
    }

    public boolean updateJob(JobConfig jobConfig) {
        //TODO : TOBE IMPLEMENT
        return true;
    }

    public boolean deleteJob(String name) {
        //TODO : TOBE IMPLEMENT
        return true;
    }

    @Bean
    public boolean loadJobs() {
        for (JobConfig j : jobService.getJobs()) {
            ScheduledFuture s = scheduler.schedule(new Runner(j), new CronTrigger(j.executionTime));
            scheduledFutures.add(s);
        }
        return true;
    }

}
