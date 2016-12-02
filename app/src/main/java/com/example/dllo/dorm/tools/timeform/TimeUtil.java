package com.example.dllo.dorm.tools.timeform;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaojun on 16/11/30.
 */

public class TimeUtil {
    //获取系统当前时间(时间戳)
    public static String getDate(){
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        return str;
    }

    //获取系统当前时间(标准格式时间)
    public static String getTime(){
        Date mDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        String time = df.format(mDate);
        return time;
    }

    //字符串转时间戳
//    public static String getTime(String timeString){
//        String timeStamp = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
//        Date d;
//        try{
//            d = sdf.parse(timeString);
//            long l = d.getTime();
//            timeStamp = String.valueOf(l);
//        } catch(ParseException e){
//            e.printStackTrace();
//        }
//        return timeStamp;
//    }

    //时间戳转字符串
//    public static String getStrTime(String timeStamp){
//        String timeString = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
//        long  l = Long.valueOf(timeStamp);
//        timeString = sdf.format(new Date(l));//单位秒
//        return timeString;
//    }
}
