package com.example.hyyx.testdemo.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bin_li on 16/6/23.
 */
public class TimeUtils {

    public static Calendar calendar = Calendar.getInstance();

    public static int getCurrentYear() {
//        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar.get(Calendar.YEAR);
    }


    public static int getCurrentaMonth() {

        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentaDate() {
        return calendar.get(Calendar.DATE);
    }


    public static int getTimeDiffer(long time1, long time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year1, month1, day1);
        time1 = calendar.getTimeInMillis();
        calendar.setTimeInMillis(time2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year2, month2, day2);
        time2 = calendar.getTimeInMillis();
        return (int) ((time1 - time2) / (24 * 3600 * 1000));
    }

    /**
     * 获取当前时间的毫秒数
     *
     * @return
     */
    public static Long getCurrentMill() {
        Date dt = new Date();
        Long time = dt.getTime();

        return time;
    }

    /**
     * 获取token失效时间的毫秒数
     *
     * @return
     */
    public static Long getLimitMill(Long timediffer) {
        Long currentTime = getCurrentMill();
        Long limitTime = currentTime + timediffer;

        return limitTime;

    }

    /**
     * @param timeStr 需要转化的时间串
     * @param type    时间串的格式
     * @return
     */
    public static Date getDateFromString(String timeStr, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param timeStr 需要转化的时间串
     * @param type    时间串的格式
     * @return
     */
    public static int getYearFromString(String timeStr, String type) {
        Date date = getDateFromString(timeStr, type);
        if (date != null)
            return date.getYear();
        else
            return -1;
    }

    /**
     * @param timeStr 需要转化的时间串
     * @param type    时间串的格式
     * @return
     */
    public static int getMonthFromString(String timeStr, String type) {
        Date date = getDateFromString(timeStr, type);
        if (date != null)
            return date.getMonth();
        else
            return -1;
    }

    /**
     * @param timeStr 需要转化的时间串
     * @param type    时间串的格式
     * @return
     */
    public static int getDayFromString(String timeStr, String type) {
        Date date = getDateFromString(timeStr, type);
        if (date != null)
            return date.getDate();
        else
            return -1;
    }

    public static String getCurDay2String() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(calendar.getTime());
        return time;

    }

    public static String getCurMon2String() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(calendar.getTime());
        return time;

    }

    /**
     * @param date 时间格式为2010-11-01
     * @return
     */
    public static String getItemDay(String date) {
        String[] split = date.split("-");
        return split[2];

    }

    public static String getMonthAndDay(String date) {
        String[] ts = date.split("T");

        String[] split = ts[0].split("-");


        return split[1] + "-" + split[2];

    }

    public static int compare_date(String DATE1, String DATE2) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


}



