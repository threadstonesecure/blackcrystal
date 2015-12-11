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


    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.GET)
    public ResponseEntity getJob(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping("/job/{name}/run")
    @ResponseBody
    public ResponseEntity runJob(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping("/job/{name}/cancel")
    @ResponseBody
    public ResponseEntity cancelJob(@PathVariable String name) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
