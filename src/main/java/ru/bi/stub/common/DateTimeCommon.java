package ru.bi.stub.util;

import ru.bi.stub.config.DateTimeConfigProperties;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeUtils {
    private static final DateTimeConfigProperties dateTimeConfigProperties = new DateTimeConfigProperties();

    public DateTimeUtils() {
        throw new RuntimeException("Couldn't initialized DateTimeUtils.");
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return new DateTimeFormatterBuilder().appendPattern(dateTimeConfigProperties.getPattern()).toFormatter();
    }
}
