package ru.urals.uralsapi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This class provides a method to parse a date string and return a LocalDate object.
 */
public class DateParser {

    private static final String dateFormat = "MM/dd/yyyy";

    /**
     * Parses the given date string and returns a LocalDate object.
     *
     * @param dateStr date string to be parsed
     * @return LocalDate date representing parsed date
     */
    public static LocalDate parseDateFromString(String dateStr){
        DateTimeFormatter form = DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.US);
        return LocalDate.parse(dateStr, form);
    }

}
