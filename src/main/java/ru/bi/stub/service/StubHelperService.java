package ru.bi.stub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class StubHelperService {
    Random random = new Random();
    private final Logger log = LoggerFactory.getLogger(StubHelperService.class);

    public Integer getTimeResponse() {
        return 1000 + random.nextInt(0, 1000);
    }

    public void sleep() {
        try {
            Thread.sleep(getTimeResponse());
        } catch (InterruptedException e) {
            log.error("The sleep method is interrupted.");
        }
    }

    public String getCurrentDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();

        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
