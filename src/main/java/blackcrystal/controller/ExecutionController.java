package blackcrystal.controller;

import blackcrystal.service.ExecutionService;
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
import org.springframework.web.bind.annotation.*;


@Controller
public class ExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionController.class);


    @Autowired
    private ExecutionService executionService;


    @ResponseBody
    @RequestMapping(value = "/job/{name}/executions", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable String name,
                                    @RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "20") Integer size) {
        return executionService.getExecutions(name, page, size)
                .map(e -> new ResponseEntity(e, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @ResponseBody
    @RequestMapping(value = "/job/{name}/execution/{executionId}")
    public ResponseEntity<?> get(@PathVariable String name, @PathVariable Integer executionId) {
        return executionService.getExecution(name, executionId)
                .map(e -> new ResponseEntity(e, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }


    @ResponseBody
    @RequestMapping(value = "/job/{name}/execution/{executionId}/log", produces = "text/plain")
    public ResponseEntity<?> getLog(@PathVariable String name, @PathVariable Integer executionId) {
        return executionService.getExecutionLogPath(name, executionId)
                .map(p -> {
                    Resource file = new FileSystemResource(p.toString());

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.TEXT_PLAIN);
                    //headers.setContentDispositionFormData("inline", name + "-" + executionId + ".log");

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
