package blackcrystal.runner;

import blackcrystal.model.JobConfig;
import blackcrystal.service.JobConfigService;
import blackcrystal.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class JobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    private Map<String, JobThreadReference> threads = new HashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobConfigService jobConfigService;


    @Bean
    private ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(50);
        return scheduler;
    }

    /**
     * Load all jobs on init, so all jobs will be scheduled immediately
     *
     * @return true
     */
    @Bean
    private boolean loadJobs() {
        jobService.getJobs().forEach(jobConfig -> addJob(jobConfig));
        return true;
    }


    /**
     * Add reference of Runner and ScheduledFuture.
     * This is needed for stopping the running process or canceling the schedule
     *
     * @param jobConfig
     * @return
     */
    public boolean addJob(JobConfig jobConfig) {
        if(jobConfig.enabled){
            Runner runner = new Runner(jobConfig, jobConfigService);
            ScheduledFuture scheduledFuture =
                    scheduler.schedule(runner, new CronTrigger(jobConfig.executionTime));
            threads.put(jobConfig.name, new JobThreadReference(runner, scheduledFuture));
            return true;
        }else{
            return false;
        }
    }

    public boolean updateJob(JobConfig jobConfig) {
        if(jobConfig.enabled){
            JobThreadReference ref = threads.get(jobConfig.name);
            //Cancel scheduler and add a new scheduler
            ref.scheduledFuture.cancel(true);
            ref.scheduledFuture = scheduler.schedule(ref.runner, new CronTrigger(jobConfig.executionTime));
            threads.put(jobConfig.name, ref);
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteJob(String name) {
        JobThreadReference ref = threads.get(name);
        ref.runner.running = false;
        ref.scheduledFuture.cancel(true);
        threads.remove(name);
        return true;
    }


}

