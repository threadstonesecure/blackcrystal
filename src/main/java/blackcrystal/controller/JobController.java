package blackcrystal.controller;

import blackcrystal.model.JobConfig;
import blackcrystal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping("/jobs")
    @ResponseBody
    public ResponseEntity<List<JobConfig>> getAll() {
        return new ResponseEntity(jobService.getJobs(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable String name) {
        return jobService.get(name)
                .map(j -> new ResponseEntity(j, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @ResponseBody
    @RequestMapping(value = "/job", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody JobConfig jobConfig) {
        if (jobService.jobExist(jobConfig)) {
            return jobService.update(jobConfig)
                    .map(j -> new ResponseEntity(j, HttpStatus.OK))
                    .orElse(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        } else {
            return jobService.create(jobConfig)
                    .map(j -> new ResponseEntity(j, HttpStatus.CREATED))
                    .orElse(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/job/{name}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String name) {
        if (jobService.jobExist(name)) {
            if (jobService.delete(name)) {
                return new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
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
