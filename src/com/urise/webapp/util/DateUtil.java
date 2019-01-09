package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String date) {
        if (date.isEmpty() || "Сейчас".equals(date)) {
            return NOW;
        }
        return LocalDate.parse(date);
    }

    public static String format(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        if (localDate.equals(NOW)) {
            return "Сейчас";
        }
        return localDate.format(DATE_FORMATTER);
    }
}
