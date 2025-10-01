package ru.bi.stub.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bi.stub.exception.InvalidJsonException;
import ru.bi.stub.model.User;
import ru.bi.stub.common.DateTimeCommon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class UserJsonMapper {
    private final DateTimeFormatter DATE_TIME_FORMAT;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserJsonMapper(DateTimeCommon dateTimeCommon) {
        this.DATE_TIME_FORMAT = dateTimeCommon.getDateTimeFormatter();

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public String toJson(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            return null;
        }
    }

    public User fromJson(String line) throws InvalidJsonException {
        String[] lines = line.split(",");
        Map<String, String> map = new HashMap<>();
        for (var l : lines) {
            String[] l1 = l.split(":", 2);
            String key = deleteCharacters(l1[0], "{", "\"", " ");

            String value = deleteCharacters(l1[1].split(" ", 2)[1], "\"", "}");
            map.put(key, value);
        }
        User user = User.builder()
                .login(map.get("login"))
                .email(map.get("email"))
                .password(map.get("password"))
                .dateTime(LocalDateTime.parse(map.get("dateTime"), DATE_TIME_FORMAT))
                .build();
        if (!isValidJson(user)) {
            throw new InvalidJsonException();
        }
        return user;
    }

    private String deleteCharacters(String value, String... target) {
        StringBuilder result = new StringBuilder(value);
        for (var tar : target) {
            int k = result.indexOf(tar);
            while (k != -1) {
                result.deleteCharAt(k);
                k = result.indexOf(tar);
            }
        }
        return result.toString();
    }

    private boolean isValidJson(User user) {
        try (ValidatorFactory validation = Validation.buildDefaultValidatorFactory()) {
            Set<ConstraintViolation<User>> set = validation.getValidator().validate(user);
            if (set.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }
}