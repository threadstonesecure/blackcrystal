package blackcrystal.service;

import blackcrystal.model.Resource;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    //TODO : improve readability
    public Optional<Resource> getResource(String name) {
        if(resourceExist(name)){
            Path p = directoryService.resourceConfigFile(name);
            try {
               return Optional.of(FileUtility.read(p, Resource.class));
            } catch (Exception e) {
                logger.error("Could not load the resource:" + p.toString(), e);
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }

    public boolean writeResourceConfig(Resource resource) {
        Path resourceConfigFile = directoryService.resourceConfigFile(resource.name);
        Path resourceDirectory = directoryService.resourceDirectory(resource.name);
        FileUtility.createDirectory(resourceDirectory);
        return FileUtility.write(resourceConfigFile, resource);
    }


    //TODO : add new resource to scheduling process
    public Optional<Resource> update(Resource resource) {
        if (writeResourceConfig(resource)) {
            return Optional.of(resource);
        } else {
            return Optional.empty();
        }
    }

    //TODO : add new resource to scheduling process
    public Optional<Resource> create(Resource resource) {
        if (writeResourceConfig(resource)) {
            return Optional.of(resource);
        } else {
            return Optional.empty();
        }
    }

    public boolean resourceExist(Resource resource) {
        return Files.exists(directoryService.resourceDirectory(resource.name));
    }
    //TODO : remove one of the resourceExist methods
    public boolean resourceExist(String name) {
        Resource resource = new Resource();
        resource.name = name;
        return resourceExist(resource);
    }

}
