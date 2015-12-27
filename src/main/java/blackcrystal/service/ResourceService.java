package blackcrystal.service;

import blackcrystal.model.Resource;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component("resourceService")
public class ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);


    @Autowired
    private DirectoryService directoryService;

    public List<Resource> loadResources() {
        List<Resource> resources = new ArrayList<>();

        List<Path> directories = FileUtility.getSubDirectories(directoryService.resourcesDirectory());
        for (Path p : directories) {
            try {
                resources.add(FileUtility.read(p.resolve("config.json"), Resource.class));
            } catch (Exception e) {
                logger.error("Could not load the resource:" + p.toString(), e);
            }
        }
        return resources;
    }

}
