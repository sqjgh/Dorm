package com.example.dllo.dorm.tools.okhttp;

/**
 * Created by zhaojun on 16/11/23.
 */

public class HttpUtil {
    public static void getContent(String page,ResponseCallBack<ContentBean> callBack){
        //获得一个真正的网络请求接口url
        String url = "http://m2.qiushibaike.com/article/list/imgrank?page=1&count=30";
        //使用Manager来发起网络请求
        OkHttpManager.getInstance().get(url,ContentBean.class,callBack);

    }
}
