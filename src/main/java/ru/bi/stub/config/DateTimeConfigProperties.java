package ru.bi.stub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "date-time")
@Getter
@Setter
public class DateTimeConfigProperties {
    private String pattern;
}
