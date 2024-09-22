package com.coral.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public static Optional<LocalDate> parseDate(String dateString) {
        try {
            return Optional.of(LocalDate.parse(dateString, FORMATTER));
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", e.getMessage());
        }
        return Optional.empty();
    }
}
