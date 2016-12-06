package com.example.dllo.dorm.tools.liteorm;

import android.os.Handler;
import android.os.Looper;

import com.example.dllo.dorm.base.MyApp;
import com.example.dllo.dorm.game.ChartBean;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by dllo on 16/11/8.
 */
public class BaseSingleton {
    //饿汉单例,,没有办法在构造方法里传值
    //懒汉单例,可以在构造里传值
    private static BaseSingleton base = new BaseSingleton();
    private LiteOrm mLiteOrm;  //初始化的时候一般都要写在单例里 不然会提示关闭之前数据库或者出奇怪的问题
    private Handler mHandler;//用来做线程之间的切换的
    private Executor mExecutorPool;//线程池


    //私有化构造
    private BaseSingleton() {
        mLiteOrm = LiteOrm.newCascadeInstance(MyApp.getContext(), "liwushuoDb.db");
        mHandler = new Handler(Looper.getMainLooper());
        //线程池
        int coreNum = Runtime.getRuntime().availableProcessors();
        //newFixedThreadPool 任务队列是无限的
        mExecutorPool = Executors.newFixedThreadPool(coreNum + 1);
    }

    public static BaseSingleton getIntstance() {
        return base;
    }

    //插入方法
    //数据库的操作一定得在子线程里调用

    //LiteOrm 的存数据库方法
    public <T> void insertData(final List<T> t) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLiteOrm.insert(t);
            }
        }).start();

    }

    //LiteOrm 删除数据库全删方法

    public void deleteAllData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLiteOrm.delete(ChartBean.class);
               // mLiteOrm.deleteDatabase();  //库文件一起干掉
               // mLiteOrm.openOrCreateDatabase();  //重建一个数据库
               // 这个方法当库里只有一个表的时候可以用但是如果表多的话会全删光的
            }
        }).start();
    }
    //LiteOrm 删除数据库定向删方法
    public void deleteSpecifyData(final String data){
        mExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                mLiteOrm.delete(new WhereBuilder(ChartBean.class).where("data = ?",data));
            }
        });
    }

    //查数据库
    public <T> void queryAllData(OnQueryListenerAll<T> monQueryListener, Class<T> tClass) {


        //简化后的
        mExecutorPool.execute(new QueryRunnable(monQueryListener, tClass));
    }

    //查数据库外部子线程1

    class CallBackRunnable<T> implements Runnable {

        OnQueryListenerAll<T> mOnQueryListener;
        List<T> tList;

        public CallBackRunnable(OnQueryListenerAll<T> mOnQueryListener, List<T> tList) {
            this.mOnQueryListener = mOnQueryListener;
            this.tList = tList;
        }

        @Override
        public void run() {
            mOnQueryListener.onQuery(tList);
        }
    }

    //查数据库外部的子线程2
    class QueryRunnable<T> implements Runnable {
        private OnQueryListenerAll<T> mOnQueryListener;
        private Class<T> tClass;

        public QueryRunnable(OnQueryListenerAll<T> mOnQueryListener, Class<T> tClass) {
            this.mOnQueryListener = mOnQueryListener;
            this.tClass = tClass;
        }

        @Override
        public void run() {

            ArrayList<T> query = mLiteOrm.query(tClass);
            mHandler.post(new CallBackRunnable(mOnQueryListener, query));

        }
    }

    //查询数据库泛型
    public interface OnQueryListenerAll<T> {
        void onQuery(List<T> tList);
    }

}
