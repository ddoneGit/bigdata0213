package com.javase.review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * @author ddone
 * @date 2020/7/23-16:33
 */
public class NewDateAPI {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();//2020-07-23
        LocalDate day1 = now.plusDays(1);//2020-07-24
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = LocalTime.now();
        System.out.println(time);
        System.out.println(dateTime);//2020-07-23T16:39:31.546
        System.out.println(day1);//2020-07-24

        LocalDate setTime = LocalDate.of(2020, 10, 12);
        System.out.println(setTime);//2020-07-23 16:39:31

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDate = dateTimeFormatter.format(dateTime);
        System.out.println(formatDate);//2020-07-23 16:39:31
    }
}
