package blackcrystal.controller;

import blackcrystal.app.ApplicationProperties;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfigController {

    @Autowired
    private ApplicationProperties properties;

    @RequestMapping("/config")
    @ResponseBody
    public ResponseEntity<?> get() {
        return new ResponseEntity(properties, HttpStatus.OK);
    }


    @RequestMapping("/config.js")
    @ResponseBody
    public ResponseEntity<?> getJs() {
        String propertiesAsJson = new Gson().toJson(properties);
        return new ResponseEntity("var config = "+propertiesAsJson+";", HttpStatus.OK);
    }
}

