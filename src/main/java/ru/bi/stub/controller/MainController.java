package ru.bi.stub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bi.stub.model.PostRequest;
import ru.bi.stub.service.StubHelperService;

@RestController
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    private StubHelperService stubHelperService;
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/get")
    public String getLogin() {
        stubHelperService.sleep();

        log.info("The getLogin method is executed.");

        return "{\"login\":\"Login1\",\"status\":\"ok\"}";
    }

    @PostMapping("/post")
    public String postLogin(@RequestBody PostRequest postRequest) {
        stubHelperService.sleep();

        String dateTime = stubHelperService.getCurrentDateTime();

        log.info("The postLogin method is executed. Date: {}.", dateTime);

        return String.format("{\"login\":\"%s\",\"password\":\"%s\",\"date\":\"%s\"}",
                postRequest.getLogin(), postRequest.getPassword(), dateTime);
    }
}
