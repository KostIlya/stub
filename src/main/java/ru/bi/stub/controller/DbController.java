package ru.bi.stub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bi.stub.exception.InvalidJsonException;
import ru.bi.stub.exception.UserNotFoundException;
import ru.bi.stub.model.User;
import ru.bi.stub.worker.DataBaseWorker;

@RestController
@RequestMapping("/api/db")
@Slf4j
public class DbController {
    @Autowired
    private final DataBaseWorker dataBaseWorker;

    public DbController(DataBaseWorker dataBaseWorker) {
        this.dataBaseWorker = dataBaseWorker;
    }

    @GetMapping("/select/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        try {
            User user = dataBaseWorker.selectUser(login);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<String> postUser(@RequestBody User user) {
        try {
            isValidJson(user);
            if (dataBaseWorker.insertUser(user)) {
                return ResponseEntity.ok("User added!");
            } else {
                return ResponseEntity.internalServerError().body("User was not added");
            }
        } catch (InvalidJsonException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    private void isValidJson(User user) throws InvalidJsonException {
        if (user.getLogin() == null || user.getPassword() == null || user.getEmail() == null || user.getDateTime() == null) {
            throw new InvalidJsonException();
        }
    }
}
