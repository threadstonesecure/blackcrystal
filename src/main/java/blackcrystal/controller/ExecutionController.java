package blackcrystal.controller;

import blackcrystal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExecutionController {

    @Autowired
    private JobService jobService;


    @ResponseBody
    @RequestMapping(value = "/job/{name}/executions", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable String name) {
        return jobService.getExecutions(name)
                .map(j -> new ResponseEntity(j, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @ResponseBody
    @RequestMapping(value = "/job/{name}/execution/{executionId}")
    public ResponseEntity<?> get(@PathVariable String name, @PathVariable Integer executionId) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @ResponseBody
    @RequestMapping(value = "/job/{name}/execution/{executionId}/log")
    public ResponseEntity<?> getLog(@PathVariable String name, @PathVariable Integer executionId) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
