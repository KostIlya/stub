package ru.bi.stub.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bi.stub.config.FileConfigProperties;
import ru.bi.stub.exception.InvalidJsonException;
import ru.bi.stub.model.User;
import ru.bi.stub.model.mapper.UserJsonMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class FileWorker {
    private final FileConfigProperties fileConfigProperties;
    private final UserJsonMapper userJsonMapper;
    @Autowired
    public FileWorker(FileConfigProperties fileConfigProperties, UserJsonMapper userJsonMapper) {
        this.fileConfigProperties = fileConfigProperties;
        this.userJsonMapper = userJsonMapper;
    }

    public void writeUser(User user) {
        try {
            Files.writeString(Path.of(fileConfigProperties.getPathWriteFile()),
                    userJsonMapper.toJson(user) + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public User readUser() throws InvalidJsonException {
        try {
            List<String> list = Files.readAllLines(Path.of(fileConfigProperties.getPathReadFile()));
            if (list.isEmpty()) {
                log.error("File is empty! Path: {}", fileConfigProperties.getPathReadFile());
                return null;
            }
            int number = new Random().nextInt(list.size());
            return userJsonMapper.fromJson(list.get(number));
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
