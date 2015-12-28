package blackcrystal.controller;

import blackcrystal.model.Resource;
import blackcrystal.model.ResourceType;
import blackcrystal.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ResponseBody
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return new ResponseEntity(resourceService.loadResources(), HttpStatus.OK);
    }


    /**
     * Return supported resource-types
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resource-types", method = RequestMethod.GET)
    public ResponseEntity<?> types() {
        return new ResponseEntity(ResourceType.values(), HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/resource", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Resource resource) {
        if (resourceService.resourceExist(resource)) {
            return resourceService.update(resource)
                    .map(r -> new ResponseEntity(r, HttpStatus.OK))
                    .orElse(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        } else {
            return resourceService.create(resource)
                    .map(r -> new ResponseEntity(r, HttpStatus.CREATED))
                    .orElse(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }


    @ResponseBody
    @RequestMapping(value = "/resource/{name}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @ResponseBody
    @RequestMapping(value = "/resource/{name}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

}

