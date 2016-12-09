package com.example.dllo.dorm.setting.loginmvp;

/**
 * Created by dllo on 16/11/22.
 * 协议类, 会把MVP三层的接口都放到这个协议类里
 * 统一管理
 * 顶层模块不要依赖底层模块
 * 二者要共同依赖抽象
 */

public interface LoginContract {
    interface View{

        /**
         * 把P层放到V层里面
         * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * 显示出用户名/ 密码 为空的提示信息
         */
        void showEmptyMsg();

        /**
         * 显示正在登录的画面
         */
        void showLoading();

        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败, 显示失败信息
         * @param msg 失败信息
         */
        void loginError(String msg);

        /**
         * 显示正在注册的画面
         */
        void showRegisterLoading();

        /**
         * 注册成功
         */
        void registerSuccess();

        /**
         * 注册失败 显示失败信息
         * @param msg 失败信息
         */
        void registerError(String msg);
    }

    interface Presenter{

        /**
         * 接收View层传过来的用户名和密码  登录
         * @param userName 用户名
         * @param password 密码
         */
        void login(String userName, String password);

        /**
         * 检测用户名或者密码是否为空
         * @param userName 用户名
         * @param password 密码
         * @return   true 为空, false 不为空
         */
        boolean checkIsEmpty(String userName, String password);

        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         * @param e 登录失败的异常信息
         */
        void loginError(Exception e);


        /**
         * 接收View层传过来的用户名和密码   注册
         * @param userName 用户名
         * @param password 密码
         */
        void register(String userName, String password);


        /**
         * 注册成功
         */
        void registerSuccess();

        /**
         * 登录失败
         * @param e 登录失败的异常信息
         */
        void registerError(Exception e);


    }

    interface Model{

        /**
         * 进行登录(耗时操作)
         * @param userName
         * @param password
         */
        void login(String userName, String password);

        /**
         * 把Presenter层放到Model里
          * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         *  进行注册
         * @param userName 账号
         * @param password
         */
        void register(String userName, String password);

        /**
         * 获得当前账号群组信息
         */
        void GroupID();

    }



}
