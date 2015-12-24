package blackcrystal.controller;

import blackcrystal.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionController.class);


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
        return jobService.getExecutionResult(name, executionId)
                .map(j -> new ResponseEntity(j, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }


    @ResponseBody
    @RequestMapping(value = "/job/{name}/execution/{executionId}/log")
    public ResponseEntity<?> getLog(@PathVariable String name, @PathVariable Integer executionId) {
        return jobService.getExecutionLogPath(name, executionId)
                .map(p -> {
                    Resource file = new FileSystemResource(p.toString());

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.setContentDispositionFormData("inline", name + "-" + executionId + ".log");

                    try {
                        InputStreamResource inputStreamResource = new InputStreamResource(file.getInputStream());
                        headers.setContentLength(file.contentLength());
                        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
                    } catch (Exception e) {
                        logger.error("Could not stream log file " + p.toString(), e);
                        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                })
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));


    }


}
