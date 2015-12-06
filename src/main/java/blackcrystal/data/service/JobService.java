package blackcrystal.data.service;

import blackcrystal.data.domain.JobConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;

public interface JobService {
	JobConfig getJob(String path) throws IOException, JsonMappingException, JsonParseException;

}
