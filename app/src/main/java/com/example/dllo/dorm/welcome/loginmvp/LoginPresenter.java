package com.example.dllo.dorm.welcome.loginmvp;

import android.text.TextUtils;

/**
 * Created by dllo on 16/11/22.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View mView, LoginContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    /**
     * 接收View层传过来的用户名和密码
     *
     * @param userName 用户名
     * @param password 密码
     */
    @Override
    public void login(String userName, String password) {
        if (checkIsEmpty(userName, password)){
            mView.showEmptyMsg();
        }else {
            mModel.login(userName, password);
            mView.showLoading();
        }
    }

    /**
     * 检测用户名或者密码是否为空
     *
     * @param userName 用户名
     * @param password 密码
     * @return true 为空, false 不为空
     */
    @Override
    public boolean checkIsEmpty(String userName, String password) {

        return TextUtils.isEmpty(userName) || TextUtils.isEmpty(password);
    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {
        mView.loginSuccess();
    }

    /**
     * 登录失败
     *
     * @param e 登录失败的异常信息
     */
    @Override
    public void loginError(Exception e) {
        if (e == null){
            mView.loginError("登录失败");
        }else {
            mView.loginError(e.getMessage());
        }

    }
}
