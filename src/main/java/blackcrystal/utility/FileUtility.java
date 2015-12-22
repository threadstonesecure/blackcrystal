package blackcrystal.utility;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileUtility {

    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

    public static <T> T read(Path path, Class<T> valueType) throws IOException, JsonMappingException, JsonParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(path);
        return objectMapper.readValue(jsonData, valueType);
    }


    public static boolean write(Path path, Object object) {
        String asJson = new Gson().toJson(object);
        try {
            Files.write(path, asJson.getBytes());
            return true;
        } catch (Exception e) {
            logger.debug("Could not write the file  : " + path.toString(), e);
            return false;
        }
    }


    public static boolean createDirectory(Path path) {
        if (Files.exists(path)) {
            return true;
        } else {
            try {
                Files.createDirectory(path);
                return true;
            } catch (Exception e) {
                logger.error("Could not create the directory", e);
                return false;
            }
        }
    }

    public static List<Path> getSubDirectories(Path directory) {
        List<Path> dirList = new ArrayList<>();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            for (Path p : dirStream) {
                if (Files.isDirectory(p)) {
                    dirList.add(p);
                }
            }
        } catch (Exception e) {
            logger.error("Could not retrieve the sub directories", e);
        }
        return dirList;
    }


}
