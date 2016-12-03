package com.example.dllo.dorm.tools.toast;

import android.os.Handler;
import android.widget.Toast;

import com.example.dllo.dorm.base.MyApp;

/**
 * Created by zhaojun on 16/11/26.
 */

public class ToastUtil {
    private static String oldMsg;
    protected static Toast toast = null;

    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showShortToast(String msg) {
        Handler mHandler = new Handler();
        if (toast == null) {
            /*这种写法如果传入Activity的实例进来，将有可能会导致Activity泄露
              因为静态工具类的生存周期*/
            /*这样的话，不管传递什么content进来，都只会引用全局唯一的Content，不会产生内存泄露*/
//            toast =Toast.makeText(mContext.getApplicationContext(), msg,
                    toast = Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });

            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        toast.show();
                    }
                });

            }
        }
        oneTime = twoTime;


    }


}

