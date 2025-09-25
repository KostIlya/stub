package ru.bi.stub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class DbConfig {
    private String url;
    private String username;
    private String password;
}
