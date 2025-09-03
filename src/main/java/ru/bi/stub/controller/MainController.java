package ru.bi.stub.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bi.stub.model.PostRequest;

import java.util.*;

@RestController
@RequestMapping(path = "/api")
@Log4j2
public class MainController {
    private static final Set<String> set = new HashSet<>();

    @GetMapping("/get")
    public ResponseEntity<String> getLogin() {
        sleep();

        String response = "{\"login\":\"Login1\",\"status\":\"ok\"}";

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/post")
    public ResponseEntity<PostRequest> postLogin(@RequestBody @Valid PostRequest postRequest) {
        sleep();

        return ResponseEntity.ok().body(postRequest);
    }

    @GetMapping("/leak")
    public ResponseEntity<String> leak() {
        set.add(new String(new char[1024*1000])); // 1mb
        return ResponseEntity.ok().body("ok");
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
