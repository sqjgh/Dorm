package com.example.dllo.dorm.tools.timeform;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaojun on 16/11/30.
 */

public class TimeUtil {
    //获取系统当前时间(时间戳)
    public static String getDate() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }

    //获取系统当前时间(标准格式时间)
    public static String getTime() {
        Date mDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        String time = df.format(mDate);
        return time;
    }

    //获取系统当前时间(年)
    public static int getTimeYear() {
        Date mDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yy年MM月dd日");
        String time = df.format(mDate);

        String year = time.substring(0, 2);

        Integer intYear = Integer.valueOf(year);

        return intYear;
    }

    //获取系统当前时间(年)
    public static int getTimeMone() {
        Date mDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yy年MM月dd日");
        String time = df.format(mDate);

        String mone = time.substring(3, 5);

        Integer intMone = Integer.valueOf(mone);

        return intMone;
    }

    //获取系统当前时间(年)
    public static int getTimeDay() {
        Date mDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yy年MM月dd日");
        String time = df.format(mDate);

        String day = time.substring(6, 8);
        Integer intDay = Integer.valueOf(day);
        return intDay;
    }
}
