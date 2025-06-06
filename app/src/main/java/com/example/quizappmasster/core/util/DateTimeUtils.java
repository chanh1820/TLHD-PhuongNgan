package com.example.quizappmasster.core.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateTimeUtils {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateTimeFormatter inputDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static final DateTimeFormatter outputDateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

    public static Integer convertTimeToInteger(Long time) {
        StringBuilder stringDate = new StringBuilder();
        String[] array = sdf.format(time).split("-");
        for (int i = 0; i < array.length; i++) {
            stringDate.append(array[i]);
        }
        return Integer.parseInt(stringDate.toString());
    }

    public static String convertLocalDateTimeToDateTime(String inputLocalDateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(inputLocalDateTime, inputDateTimeFormatter);
        return localDateTime.format(outputDateTimeFormatter);
    }
}
