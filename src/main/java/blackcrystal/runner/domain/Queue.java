package blackcrystal.runner.domain;


import blackcrystal.data.domain.JobConfig;

public class Queue {

    public Queue(JobConfig jobConfig){
        this.jobConfig = jobConfig;
    }

    public JobConfig jobConfig;

}
