package com.example.dllo.dorm;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Wanghuan on 16/11/26.
 */
public class LoginActivity extends BaseActivity {
    private EditText userId, userPassword;
    private Button mLogin;
    private String mName;
    private String mPassword;
    private Boolean needRegister = true;
    private TextView mRegister;

    @Override
    protected void initViews() {


        userId = bindView(R.id.id);
        userPassword = bindView(R.id.password);
        mLogin = bindView(R.id.user_login);
        mRegister = bindView(R.id.user_register);

    }

    @Override
    protected void initData() {

        //登录
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = userId.getText().toString();
                mPassword = userPassword.getText().toString();
                //登录
                userLogin();
            }
        });

        //注册
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = userId.getText().toString();
                mPassword = userPassword.getText().toString();
                //注册
                userRegister();
            }
        });
    }



    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    //登录
    private void userLogin() {
        MyUser myUser = new MyUser();
        myUser.setUsername(mName);
        myUser.setPassword(mPassword);
        myUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    ToastUtil.showShortToast("登录成功");

                    Values.USER_NAME = mName;

                    EventContent event = new EventContent();
                    event.setUserName(mName);
                    event.setUserPassword(mPassword);
                    EventBus.getDefault().postSticky(event);
                    onBackPressed();
                    finish();
                } else {
                    ToastUtil.showShortToast("登录失败");
                    Log.d("MainActivity", e.getMessage());
                }
            }
        });
    }


    //      注册
    private void userRegister() {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(mName);
        bmobUser.setPassword(mPassword);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    //注册成功
                    ToastUtil.showShortToast("注册成功");

                } else {
                    //注册失败
                    ToastUtil.showShortToast(e.getMessage() + "注册失败");
                    Log.d("ssssLoginActivity", e.getMessage());
                }
            }
        });
    }


}
