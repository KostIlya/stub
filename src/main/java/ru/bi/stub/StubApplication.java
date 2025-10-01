package ru.bi.stub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.bi.stub.config.DateTimeConfigProperties;
import ru.bi.stub.config.DbConfigProperties;
import ru.bi.stub.config.FileConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({DbConfigProperties.class, FileConfigProperties.class, DateTimeConfigProperties.class})
public class StubApplication {

	public static void main(String[] args) {
		SpringApplication.run(StubApplication.class, args);
	}
}
