package ru.bi.stub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileConfigProperties {
    private String pathWriteFile;
    private String pathReadFile;
}
