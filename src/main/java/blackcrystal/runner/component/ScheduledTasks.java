package blackcrystal.runner.component;

import blackcrystal.runner.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private QueueService queueService;


    //TODO get pool size from application config
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        return executor;
    }


    @Scheduled(fixedRate = 2500)
    public void reportCurrentTime() {
        if(queueService.hasNext()){
            taskExecutor.execute(new Runner(queueService.getNext()));
        }
    }
    
}
