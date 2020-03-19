package com.example.lilactests.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.lilactests.app.LilacTestsApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  Created by wing on 2016/4/19.
 */
public class TimeUtils {
    private static final SimpleDateFormat mSimpleDateTimeFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat mSimpleDateFormat
            = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat mSimpleTimeFormat
            = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat mSimpleTextFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Format DateTime to String
     *
     * @param date (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String formatDateTime(Date date) {
        return mSimpleDateTimeFormat.format(date);
    }

    /**
     * Format Date to String
     *
     * @param date (yyyy-MM-dd)
     * @return
     */
    public static String formatDate(Date date) {
        return mSimpleDateFormat.format(date);
    }

    /**
     * Format Time to String
     *
     * @param date (HH:mm)
     * @return
     */
    public static String formatTime(Date date) {
        return mSimpleTimeFormat.format(date);
    }

    /**
     * parse String to DateTime
     *
     * @param dateStr (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static Date parse(String dateStr) {
        try {
            return mSimpleDateTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("date formatDateTime error");
        }
    }

    /**
     * parse String to DateTime
     *
     * @param dateStr (yyyy-MM-dd HH:mm)
     * @return
     */
    public static Date parseText(String dateStr) {
        try {
            return mSimpleTextFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("date formatDateTime error");
        }
    }

    /**
     * parse String to Time
     *
     * @param dateStr (HH:mm)
     * @return
     */
    public static Date parseTime(String dateStr) {
        try {
            return mSimpleTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("date formatDateTime error");
        }
    }

    /**
     * parse String to Date
     *
     * @param dateStr (yyyy-MM-dd)
     * @return
     */
    public static Date parseDate(String dateStr) {
        try {
            return mSimpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("date formatDateTime error");
        }
    }
    /**
     * determine the two calendar whether the same day
     * @return true is the same day ,false else.
     */
    public static boolean isSameDay(Calendar tvCalendar, Calendar today) {
        return tvCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && tvCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && tvCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Format DateTime to String
     *
     * @param
     * @return  get now date
     */
    public static Date getNowDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date mNow = new Date();

        String time = TimeUtils.formatTime(calendar.getTime());
        String date = TimeUtils.formatDate(new Date(calendar.getTimeInMillis()));
        String now = String.valueOf(date) + " " +
                String.valueOf(time);
        if (!TextUtils.isEmpty(now.trim())) {
            mNow = TimeUtils.parseText(now);
        }
        return mNow;
    }

}
