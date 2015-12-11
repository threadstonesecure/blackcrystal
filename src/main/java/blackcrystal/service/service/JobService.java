package blackcrystal.service.service;

import blackcrystal.model.JobConfig;

import java.util.List;
//TODO : Change the package path to something mcuh more reasonable
public interface JobService {
    JobConfig getJob(String name);

    List<JobConfig> getJobs();

}
