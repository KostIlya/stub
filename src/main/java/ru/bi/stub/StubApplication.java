package ru.bi.stub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.bi.stub.config.DbConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(DbConfigProperties.class)
public class StubApplication {

	public static void main(String[] args) {
		SpringApplication.run(StubApplication.class, args);
	}

}
