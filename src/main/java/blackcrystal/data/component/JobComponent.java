package blackcrystal.data.component;

import blackcrystal.data.domain.Job;
import blackcrystal.data.repository.JobRepository;
import blackcrystal.data.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("jobService")
@Transactional
class JobComponent implements JobService {

	private final JobRepository jobRepository;

	@Autowired
	public JobComponent(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	public Job getJob(Long id) {
		return this.jobRepository.findOne(id);
	}


}
