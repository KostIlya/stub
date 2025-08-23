package ru.bi.stub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bi.stub.model.PostRequest;

import java.util.Random;

@RestController
@RequestMapping(path = "/api")
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/get")
    public ResponseEntity<String> getLogin() {
        sleep();

        String response = "{\"login\":\"Login1\",\"status\":\"ok\"}";

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<PostRequest> postLogin(@RequestBody PostRequest postRequest) {
        sleep();

        return new ResponseEntity<>(postRequest, HttpStatus.OK);
    }

    public void sleep() {
        Random random = new Random();
        int timeResponse = 1000 + random.nextInt(0, 1000);
        try {
            Thread.sleep(timeResponse);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
