package com.fybgame.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:fyb
 * @Date: 2021/1/1 3:27
 * @Version:1.0
 */
public class MyTime {
    public final static String defaultTime = "1970-01-01";
    private static String[] parsePatterns = {"yyyy-MM-dd","yyyy年MM月dd日",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};
    public static String timeStamp(String time){
        if(time!=null){
            Date date = new Date(Long.parseLong(time)*1000L);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        }
        return null;
    }
    public static String getCurrentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    public static String getCurrentTimeDate(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public static boolean isDate(String time){
        if(time == null){
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(format.parse(time));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public static Date stringToDate(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
