package blackcrystal.data.component;

import blackcrystal.utility.FileUtility;
import blackcrystal.app.ApplicationProperties;
import blackcrystal.data.domain.JobConfig;
import blackcrystal.data.service.JobService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component("jobService")
@Transactional
class JobComponent implements JobService {

	public JobComponent() {

	}


	@Autowired
	private ApplicationProperties properties;

	@Override
	public JobConfig getJob(String path) throws IOException, JsonMappingException, JsonParseException {
		return FileUtility.getJobConfig(properties.getWorkspace() + "/jobs/" + path + "/config.json");
	}






}
