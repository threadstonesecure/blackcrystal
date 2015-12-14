package blackcrystal.runner;

import java.util.concurrent.ScheduledFuture;


public class JobThreadReference {

    public Runner runner;
    public ScheduledFuture scheduledFuture;


    public JobThreadReference(Runner runner, ScheduledFuture scheduledFuture) {
        this.runner = runner;
        this.scheduledFuture = scheduledFuture;
    }
}
