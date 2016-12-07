package com.example.dllo.dorm.welcome.loginmvp;

import android.text.TextUtils;

/**
 * Created by dllo on 16/11/22.
 * 主持人（Presenter）：相当于协调者，是模型与视图之间的桥梁，将模型与视图分离开来。
 *              进行中间步骤的判断, 避免一些不必要的错误, 使V M层各自处理的逻辑更清晰简洁
 * P 层
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
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void login(String username, String password) {
        if (checkIsEmpty(username, password)){
            mView.showEmptyMsg();
        }else {
            mModel.login(username, password);
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

    /**
     * 接收View层传过来的用户名和密码   注册
     *
     * @param userName 用户名
     * @param password 密码
     */
    @Override
    public void register(String userName, String password) {
        if (checkIsEmpty(userName, password)){
            mView.showEmptyMsg();
        }else {
            mView.showLoading();
            mModel.register(userName, password);
        }

    }

    /**
     * 注册成功
     */
    @Override
    public void registerSuccess() {
        mView.registerSuccess();
    }

    /**
     * 登录失败
     *
     * @param e 登录失败的异常信息
     */
    @Override
    public void registerError(Exception e) {
        if (e == null){
            mView.registerError("登录失败");
        }else {
            mView.registerError(e.getMessage());
        }
    }

}
