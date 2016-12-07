package com.example.dllo.dorm.welcome.loginmvp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.dllo.dorm.MyUser;
import com.example.dllo.dorm.base.Values;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dllo on 16/11/22.
 * // 模型（Model）：负责处理数据的加载或者存储，比如从网络或本地数据库获取数据等；
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
        BMOB_REGISTER = false;
        BMOB_LOGIN = false;
        HUANXIN_REGISTER = false;
        HUANXIN_LOGIN = false;
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
                        GroupID();
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
                    GroupID();
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

    /**
     * 进行注册
     *
     * @param userName 账号
     * @param password
     */
    @Override
    public void register(final String userName, final String password) {
        BMOB_REGISTER = false;
        BMOB_LOGIN = false;
        HUANXIN_REGISTER = false;
        HUANXIN_LOGIN = false;


        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(userName);
        bmobUser.setPassword(password);
        //注意：不能用save方法进行注册
        bmobUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    BMOB_REGISTER = true;
                    if (BMOB_REGISTER && HUANXIN_REGISTER) {
                        // 注册成功
                        Values.USER_NAME = userName;
                        mPresenter.registerSuccess();
                    }

                } else {
                    BMOB_REGISTER = false;
                    if (!(BMOB_REGISTER && HUANXIN_REGISTER)) {
                        // 注册失败
                        Exception exception = new Exception("注册失败, 请重试");
                        mPresenter.registerError(exception);
                    }
                }
            }
        });


        //注册失败会抛出HyphenateException
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, password);//同步方法
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    HUANXIN_REGISTER = false;
                    Log.d("LoginModel", "环信注册失败");
                }
            }
        }).start();

        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                HUANXIN_REGISTER = true;
                if (BMOB_REGISTER && HUANXIN_REGISTER) {
                    // 登录成功
                    Values.USER_NAME = userName;
                    mPresenter.registerSuccess();
                }
            }

            @Override
            public void onError(int i, String s) {
                HUANXIN_LOGIN = false;
                if (!(BMOB_REGISTER && HUANXIN_REGISTER)) {
                    // 登录失败
                    Exception exception = new Exception("注册失败, 请重试");
                    mPresenter.registerError(exception);
                }
            }

            @Override
            public void onProgress(int i, String s) {
                Log.d("1111111", "i:" + i + "s" + s);
            }
        });

    }

    /**
     * 获得当前账号群组信息
     */
    @Override
    public void GroupID() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    List<EMGroup> groupList = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    if (groupList.size() > 0){
                        //根据群组ID从服务器获取群组基本信息
                        EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupList.get(0).getGroupId() + "");
                        Values.GROUP_MEMBERS = group.getMembers(); // 获取群成员
                        Log.d("LoginModel333", "Values.GROUP_MEMBERS:" + Values.GROUP_MEMBERS);
                        Log.d("LoginModel333", Values.GROUP_OWNER);
                        Values.GROUP_OWNER = group.getOwner();// 获取群主
                        if (Values.GROUP_OWNER == Values.USER_NAME){
                            Values.OWNER = true;
                        }
                    }else {
                        Values.GROUP_MEMBERS = null; // 群成员清空
                        Values.GROUP_OWNER = "";// 群主清空
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
