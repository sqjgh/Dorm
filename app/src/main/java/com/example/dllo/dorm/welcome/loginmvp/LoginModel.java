package com.example.dllo.dorm.welcome.loginmvp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.dllo.dorm.MyUser;
import com.example.dllo.dorm.base.Values;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dllo on 16/11/22.
 */

public class LoginModel implements LoginContract.Model {
    private LoginContract.Presenter mPresenter;
    private Handler handler;


    private Boolean needRegister = true;

    private boolean BMOB_REGISTER = false;
    private boolean BMOB_LOGIN = false;
    private boolean HUANXIN_REGISTER = false;
    private boolean HUANXIN_LOGIN = false;


    public LoginModel() {
        handler = new Handler(Looper.getMainLooper()); // 调回主线程
    }

    /**
     * 进行登录(耗时操作)
     *
     * @param userName
     * @param password
     */
    @Override
    public void login(final String userName, final String password) {
        MyUser myUser = new MyUser();
        myUser.setUsername(userName);
        myUser.setPassword(password);
        myUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    BMOB_LOGIN = true;
                    if (BMOB_LOGIN && HUANXIN_LOGIN) {
                        // 登录成功
                        Values.USER_NAME = userName;
                        mPresenter.loginSuccess();
                    }

                } else {
                    BMOB_LOGIN = false;
                    if (!(BMOB_LOGIN && HUANXIN_LOGIN)) {
                        // 登录失败
                        Exception exception = new Exception("用户名/密码错误");
                        mPresenter.loginError(exception);
                    }
                }
            }
        });

        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                HUANXIN_LOGIN = true;
                if (BMOB_LOGIN && HUANXIN_LOGIN) {
                    // 登录成功
                    Values.USER_NAME = userName;
                    mPresenter.loginSuccess();
                }
            }

            @Override
            public void onError(int i, String s) {
                HUANXIN_LOGIN = false;
                if (!(BMOB_LOGIN && HUANXIN_LOGIN)) {
                    // 登录失败
                    Exception exception = new Exception("用户名/密码错误");
                    mPresenter.loginError(exception);
                }
            }

            @Override
            public void onProgress(int i, String s) {
                Log.d("1111111", "i:" + i + "s" + s);
            }
        });

    }

    /**
     * 把Presenter层放到Model里
     *
     * @param presenter
     */
    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;

    }
}
