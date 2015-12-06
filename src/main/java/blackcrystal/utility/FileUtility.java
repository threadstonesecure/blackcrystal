package blackcrystal.utility;

import blackcrystal.data.domain.JobConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtility {

    public static JobConfig getJobConfig(String path) throws IOException, JsonMappingException, JsonParseException {
        return readValue(Paths.get(path), JobConfig.class);
    }

    public static <T> T readValue(Path path, Class<T> valueType) throws IOException, JsonMappingException, JsonParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(path);
        return objectMapper.readValue(jsonData, valueType);
    }

}
