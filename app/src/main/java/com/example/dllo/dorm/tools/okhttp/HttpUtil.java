package com.example.dllo.dorm.tools.okhttp;

/**
 * Created by zhaojun on 16/11/23.
 */

public class HttpUtil {
    public static void getTest(int page,ResponseCallBack<ContentBean> callBack){
        //获得一个真正的网络请求接口url
        String url = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=2&per=10";
        //使用Manager来发起网络请求
        OkHttpManager.getInstance().get(url,ContentBean.class,callBack);
    }
}
