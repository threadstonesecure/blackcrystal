package blackcrystal.runner;

import blackcrystal.model.JobConfig;
import blackcrystal.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class JobScheduler implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    private Map<String, JobThreadReference> threads = new HashMap<>();

    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private JobService jobService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

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
        if (jobConfig.enabled) {
            Runner runner = new Runner(jobConfig);
            //Add runner into spring context so it can auto-wire other dependencies
            beanFactory.autowireBean(runner);
            beanFactory.initializeBean(runner, runner.toString());

            ScheduledFuture scheduledFuture =
                    scheduler.schedule(runner, new CronTrigger(jobConfig.executionTime));
            threads.put(jobConfig.name, new JobThreadReference(runner, scheduledFuture));
            return true;
        } else {
            return false;
        }
    }


    public boolean updateJob(JobConfig jobConfig) {
        if (jobConfig.enabled) {
            JobThreadReference ref = threads.get(jobConfig.name);
            //Cancel scheduler and add a new scheduler
            ref.scheduledFuture.cancel(true);
            ref.scheduledFuture = scheduler.schedule(ref.runner, new CronTrigger(jobConfig.executionTime));
            threads.put(jobConfig.name, ref);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteJob(String name) {
        if (threads.containsKey(name)) {
            JobThreadReference ref = threads.get(name);
            ref.runner.running = false;
            ref.scheduledFuture.cancel(true);
            threads.remove(name);
            return true;
        } else {
            return false;
        }
    }

}

