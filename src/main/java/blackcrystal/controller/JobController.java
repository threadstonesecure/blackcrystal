package blackcrystal.controller;

import blackcrystal.service.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping("/jobs")
    @ResponseBody
    public ResponseEntity getAll() {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.PUT)
    public ResponseEntity put(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.POST)
    public ResponseEntity post(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping("/job/{name}/run")
    @ResponseBody
    public ResponseEntity run(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping("/job/{name}/cancel")
    @ResponseBody
    public ResponseEntity cancel(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping("/job/{name}/reload")
    @ResponseBody
    public ResponseEntity reload(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
