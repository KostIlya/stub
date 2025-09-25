package ru.bi.stub.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    @NotBlank
    @Size(min=4,max=10)
    private String login;
    @NotBlank
    @Size(min=4,max=255)
    private String email;
    @NotBlank
    @Size(min=4,max=16)
    private String password;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
}
