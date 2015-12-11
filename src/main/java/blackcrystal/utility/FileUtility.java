package blackcrystal.utility;

import blackcrystal.data.domain.JobConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;


public class FileUtility {

    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);


    public static JobConfig getJobConfig(String path) throws IOException, JsonMappingException, JsonParseException {
        return readValue(Paths.get(path), JobConfig.class);
    }
    

    public static <T> T readValue(Path path, Class<T> valueType) throws IOException, JsonMappingException, JsonParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(path);
        return objectMapper.readValue(jsonData, valueType);
    }

    public static List<Path> getSubDirectories(String directory) {
        List<Path> dirList = new ArrayList<>();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path p : dirStream) {
                if(Files.isDirectory(p)){
                    dirList.add(p);
                }
            }
        } catch (Exception e) {
            logger.error("Could not retrieve the sub directories", e);
        }
        return dirList;
    }


}
