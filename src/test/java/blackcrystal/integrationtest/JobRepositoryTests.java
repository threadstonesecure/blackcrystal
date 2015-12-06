package blackcrystal.integrationtest;

import blackcrystal.Application;
import blackcrystal.data.domain.Job;
import blackcrystal.data.repository.JobRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class JobRepositoryTests {

	@Autowired
    JobRepository repository;

	@Test
	public void getJob() {
		Job job = this.repository.findOne(1L);
		assertThat(job.getName(), is("TestJob"));
	}
}
