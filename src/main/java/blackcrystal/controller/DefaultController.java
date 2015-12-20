package blackcrystal.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity get() throws Exception {
        return ResponseEntity.status(HttpStatus.FOUND).
                header(HttpHeaders.LOCATION, "/ui/index.html").build();
    }

    @RequestMapping("/ui")
    @ResponseBody
    public ResponseEntity getUI() throws Exception {
        return ResponseEntity.status(HttpStatus.FOUND).
                header(HttpHeaders.LOCATION, "/ui/index.html").build();
    }

}

