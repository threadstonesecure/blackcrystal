package blackcrystal.service.component;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.model.JobConfig;
import blackcrystal.service.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("jobService")
@Transactional
class JobComponent implements JobService {


    @Autowired
    private ApplicationProperties properties;

    @Override
    public JobConfig getJob(String name) {
        return null;
    }

    @Override
    public List<JobConfig> getJobs() {
        return null;
    }
}
