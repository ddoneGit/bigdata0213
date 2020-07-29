package com.javase.review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ddone
 * @date 2020/7/23-15:57
 */
public class DateAndCalendar {
    public static void main(String[] args) throws ParseException {
//        System.out.println(calendar.get(Calendar.DAY_OF_WEEK)-1);//从周日开始
//        calendar.set(Calendar.DAY_OF_MONTH,18);
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//        Date time = calendar.getTime();
//        String timeStr = sdf.format(time);

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        calendar.add(Calendar.DAY_OF_MONTH,2);
//        System.out.println(sdf.format(calendar.getTime()));
        //将字符串”2020-03-15”转换为java.sql.Date对象
        String strTime = "2020-03-15";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date uitlDate = sdf.parse(strTime);
        java.sql.Date sqlDate = new java.sql.Date(uitlDate.getTime());
        System.out.println(sqlDate);
    }
    }

