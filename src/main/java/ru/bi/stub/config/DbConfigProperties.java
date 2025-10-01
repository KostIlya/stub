package ru.bi.stub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db")
@Getter
@Setter
public class DbConfigProperties {
    private String url;
    private String username;
    private String password;
}
