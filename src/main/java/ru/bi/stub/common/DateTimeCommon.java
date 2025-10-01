package ru.bi.stub.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bi.stub.config.DateTimeConfigProperties;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Component
public class DateTimeCommon {
    private final DateTimeConfigProperties dateTimeConfigProperties;

    @Autowired
    public DateTimeCommon(DateTimeConfigProperties dateTimeConfigProperties) {
        this.dateTimeConfigProperties = dateTimeConfigProperties;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return new DateTimeFormatterBuilder().appendPattern(dateTimeConfigProperties.getPattern()).toFormatter();
    }
}
