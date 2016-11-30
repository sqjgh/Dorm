package com.example.dllo.dorm.base;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by dllo on 16/11/22.
 */

public class MyApp extends Application {
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Bmob.initialize(this,"9dafd12823fac1e51761438708a48425");
    }

    public static Context getContext(){
        return sContext;
    }
}
