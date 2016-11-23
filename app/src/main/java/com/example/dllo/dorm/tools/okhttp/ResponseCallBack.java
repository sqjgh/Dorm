package com.example.dllo.dorm.tools.okhttp;

/**
 * Created by zhaojun on 16/11/23.
 */

public interface ResponseCallBack<Bean> {
    //请求成功直接返回数据类
    void OnResponse(Bean bean);

    //请求失败返回异常信息
    void onError(Exception throwable);
}
