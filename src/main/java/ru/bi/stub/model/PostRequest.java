package ru.bi.stub.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostRequest {
    private String login;
    private String password;
    private final String dateTime;

    public PostRequest(String login, String password) {
        this.login = login;
        this.password = password;
        this.dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getDateTime() {
        return dateTime;
    }
}
