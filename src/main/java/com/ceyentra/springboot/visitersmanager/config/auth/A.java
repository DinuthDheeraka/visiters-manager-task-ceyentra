/**
 * @author :  Dinuth Dheeraka
 * Created : 7/17/2023 9:11 AM
 */
package com.ceyentra.springboot.visitersmanager.config.auth;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class A {

    public static void main(String[] args) {

//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
////        System.out.println(dateFormat.format(new Date(System.currentTimeMillis())));
//
//        String format = dateFormat.format(LocalTime.now());
//
//        System.out.println(LocalTime.parse(format));


//
//        int year = 2023;
//        int month = 7;
//        int day = 17;
//
//        // Create a Calendar instance and set the desired date
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month - 1, day); // Month is zero-based
//
//        // Get the Date object from the Calendar instance
//        Date date = calendar.getTime();
//
//        // Get the milliseconds value
//        long milliseconds = date.getTime();
//
//        System.out.println("Milliseconds since Unix epoch: " + milliseconds);
//
//        System.out.println(new Date(milliseconds));

        try {
            Date d = new Date(null);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
