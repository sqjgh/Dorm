package com.example.dllo.dorm.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/11/22.
 */

public class MyApp extends Application {
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext(){
        return sContext;
    }
}
