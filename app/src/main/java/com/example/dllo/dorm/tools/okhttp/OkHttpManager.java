package com.example.dllo.dorm.tools.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhaojun on 16/11/23.
 */

public class OkHttpManager {
    //饿汉单例
    private static OkHttpManager ourInstance = new OkHttpManager();

    private OkHttpClient mClient;
    private Handler mHandler;  //用来做线程切换 Handler都是OS包下的
    private Gson mGson;

    public static OkHttpManager getInstance() {
        if (ourInstance == null) {
            synchronized (OkHttpManager.class) {
                if (ourInstance == null) {
                    ourInstance = new OkHttpManager();
                }
            }
        }
        return ourInstance;
    }

    private OkHttpManager() {
        mClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());  //括号里的参数可以保证回调在在主线程
        mGson = new Gson();
    }

    public <Bean> void post(
            String url,
            Class<Bean> clazz,
            ResponseCallBack<Bean> responseCallBack,
            HashMap<String, String> body) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        //循环添加数据
        for (String s : body.keySet()) {
            formBuilder.add(s, body.get(s));
        }
        //处理完了 post请求的body部分
        FormBody formBody = formBuilder.build();

        Request postRequest = new Request.Builder()
                .url(url)
                .post(formBody)  //把body放到request里
                .build();
        sendHttpRequest(postRequest, clazz, responseCallBack);
    }

    private <Bean> void sendHttpRequest(Request request,
                                        final Class<Bean> clazz,
                                        final ResponseCallBack<Bean> responseCallBack) {
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //网络请求失败
                mHandler.post(new ErrorRunnable<Bean>(responseCallBack, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //网络请求成功
                String data = response.body().string();
                //尝试解析
                try { //防止因为奇葩的数据导致失败
                    Bean bean = mGson.fromJson(data, clazz);
                    mHandler.post(new ResponeRunnable<Bean>(responseCallBack, bean));
                } catch (Exception e) {
                    e.printStackTrace();  //把错误信息直接输出
                    mHandler.post(new ErrorRunnable<Bean>(responseCallBack, e));
                }
            }
        });
    }

    public <Bean> void get(String url, final Class<Bean> clazz,   //这里的Bean就是泛型的名字
                           final ResponseCallBack<Bean> responseCallBack) {
        //构建Request对象
        final Request request = new Request.Builder().url(url).build();
        //发起网络请求
        sendHttpRequest(request,clazz,responseCallBack);
    }

    //请求成功Runnable和请求失败Runnable的父类
    abstract class HTTPRunnable<Bean> implements Runnable {
        protected ResponseCallBack<Bean> mResponseCallBack;

        public HTTPRunnable(ResponseCallBack<Bean> responseCallBack) {
            mResponseCallBack = responseCallBack;
        }
    }

    class ErrorRunnable<Bean> extends HTTPRunnable<Bean> {

        private Exception mException;

        public ErrorRunnable(ResponseCallBack<Bean> responseCallBack, Exception e) {
            super(responseCallBack);
            mException = e;
        }

        @Override
        public void run() {
            mResponseCallBack.onError(mException);
        }
    }

    class ResponeRunnable<Bean> extends HTTPRunnable<Bean> {

        private Bean mBean;

        public ResponeRunnable(ResponseCallBack<Bean> responseCallBack, Bean bean) {
            super(responseCallBack);
            this.mBean = bean;
        }

        @Override
        public void run() {
            mResponseCallBack.OnResponse(mBean);
        }
    }
}
