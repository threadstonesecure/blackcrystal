package blackcrystal.controller;

import blackcrystal.data.domain.Job;
import blackcrystal.data.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JobController {

	@Autowired
	private JobService jobService;

	@RequestMapping("/job")
	@ResponseBody
	@Transactional(readOnly = true)
	public Job job() {
		return this.jobService.getJob(1L);
	}

}
