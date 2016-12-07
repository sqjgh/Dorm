package com.example.dllo.dorm.base;

import java.util.List;

/**
 * Created by zhaojun on 16/11/25.
 */

public class Values {

    //探探界面图片网址
    public static final String TT_IMAGE_URL_FRONT = "http://pic.qiushibaike.com/system/pictures/";
    public static final String TT_IMAGE_URL_CENTRE = "/medium/app";
    public static final String TT_IMAGE_URL_LAST = ".webp";
    //糗百接口
    public static final String QB_URL_FRONT = "http://m2.qiushibaike.com/article/list/imgrank?page=";
    public static final String QB_URL_LAST = "&count=30";

    // 是否是群主
    public static boolean OWNER = false;
    // 群内成员
    public static List<String> GROUP_MEMBERS;
    // 群主
    public static String GROUP_OWNER = "";
    // 群组ID
    public static String GROUP_ID = "";
    // 用户名
    public static String USER_NAME = "";
    // 自动登录
    public static boolean AUTO_LOGIN = false;

}
