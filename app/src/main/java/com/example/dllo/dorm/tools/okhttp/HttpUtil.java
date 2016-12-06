package com.example.dllo.dorm.tools.okhttp;

import android.util.Log;


import com.example.dllo.dorm.express.ExpressNetApi;
import com.example.dllo.dorm.todayinhistory.HistoryBean;
import com.example.dllo.dorm.todayinhistory.HistoryNetApi;
import com.example.dllo.dorm.base.Values;


/**
 * Created by zhaojun on 16/11/23.
 */

public class HttpUtil {
    public static void getContent(String pageNum, ResponseCallBack<ContentBean> callBack) {
        //获得一个真正的网络请求接口url

        String url = Values.QB_URL_FRONT + pageNum + Values.QB_URL_LAST;

        //使用Manager来发起网络请求
        OkHttpManager.getInstance().get(url, ContentBean.class, callBack);
    }


    public static void getInfo(String COMPANY_INFO, String NUMBER_INFO, ResponseCallBack<InfoBean> infoCallBack) {
        //获得一个真正的网络请求接口url
        String url = ExpressNetApi.Base_URL + COMPANY_INFO + "&id=" + NUMBER_INFO;
        //String url1 = "http://api.avatardata.cn/ExpressNumber/Lookup?key=3b61c0d3fee0428a9b7d1fb41ab72ba4&company=tiantian&id=550513890084";
        //使用Manager来发起网络请求
        Log.d("HttpUtil", url);
        OkHttpManager.getInstance().get(url, InfoBean.class, infoCallBack);

    }


    public static  void gethistory( String keyWords ,ResponseCallBack<HistoryBean> historyCallBack){

        String url = HistoryNetApi.Base_URL+keyWords;

        Log.d("HttpUtil__________", url);
        OkHttpManager.getInstance().get(url,HistoryBean.class,historyCallBack);


    }
}
