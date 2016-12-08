package com.example.dllo.dorm.tools.toast;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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

        String threadName = Thread.currentThread().getName();
        Log.d("yyyyyToastUtil", threadName);

        if ("main".equals(threadName)) {
            mainThreadToastShow(msg);
        } else {
            otherThreadToastShow(msg);
        }

    }

    private static void otherThreadToastShow(final String msg) {
        //  Looper.prepare();
        Handler mHandler = new Handler(Looper.getMainLooper());  //主线程

        if (toast == null) {
            /*这种写法如果传入Activity的实例进来，将有可能会导致Activity泄露
              因为静态工具类的生存周期*/
            /*这样的话，不管传递什么content进来，都只会引用全局唯一的Content，不会产生内存泄露*/
//            toast =Toast.makeText(mContext.getApplicationContext(), msg,
        //    toast = new Toast(MyApp.getContext());
           // toast.setDuration(Toast.LENGTH_SHORT);
          //  toast = Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                   // toast.setText(msg);
                    //toast.show();
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


                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        oldMsg = msg;
                        toast.setText(msg);
                        toast.show();
                    }
                });
            }
        }
        oneTime = twoTime;

        //  Looper.loop();
    }

    private static void mainThreadToastShow(String msg) {
        if (toast == null) {
            /*这种写法如果传入Activity的实例进来，将有可能会导致Activity泄露
              因为静态工具类的生存周期*/
            /*这样的话，不管传递什么content进来，都只会引用全局唯一的Content，不会产生内存泄露*/
//            toast =Toast.makeText(mContext.getApplicationContext(), msg,
            toast = Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT);

            toast.show();

            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {

                    toast.show();

                }
            } else {
                oldMsg = msg;
                toast.setText(msg);

                toast.show();

            }
        }
        oneTime = twoTime;
    }

}