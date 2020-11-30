package com.kashuba.petproject.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The Date converter.
 * <p>
 * Converts a {@code LocalDate} object to a long value and vice versa.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class DateConverter {

    private DateConverter() {
    }

    /**
     * Converts a {@code LocalDate} object to a long value.
     *
     * @param date the date
     * @return the long
     */
    public static long convertToLong(LocalDate date) {
        ZonedDateTime zonedDateTime = date.atStartOfDay().atZone(ZoneId.systemDefault());

        return zonedDateTime.toInstant().toEpochMilli();
    }

    /**
     * Converts a long value to {@code LocalDate} object.
     *
     * @param milliseconds the milliseconds
     * @return the local date
     */
    public static LocalDate convertToDate(long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
