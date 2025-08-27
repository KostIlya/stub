package ru.bi.stub.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@EqualsAndHashCode
public class PostRequest {
    @Setter
    @NotBlank
    @Size(min=4,max=10)
    private String login;

    @Setter
    @NotBlank
    @Size(min=6,max=16)
    private String password;

    private final String dateTime;

    public PostRequest(String login, String password) {
        this.login = login;
        this.password = password;
        this.dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
