package com.example.dllo.dorm;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Wanghuan on 16/11/26.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private String mName;
    private String mPassword;
    private Boolean needRegister = true;

    private boolean BMOB_REGISTER = false;
    private boolean BMOB_LOGIN = false;
    private boolean HUANXIN_REGISTER = false;
    private boolean HUANXIN_LOGIN = false;


    private EditText id;
    private EditText passWord;
    private Button btn;
    private String ID;
    private String PASSWORD;
    private TextView warnId;
    private TextView warnPassWord;
    private EditText idRegister;
    private Button btnRegister;
    private TextView registerTv;
    private boolean vis = false;
    private ImageView back;
    private boolean IDCheck;
    private boolean passWordCheck;
    private Button cancel;
    private RelativeLayout alreadyLogin;
    private RelativeLayout loginRl;
    private TextView alreadyID;
    @Override
    protected void initViews() {


        // 注册主界面
        loginRl = bindView(R.id.login_login);
        id = bindView(R.id.et_id_login);
        passWord = bindView(R.id.et_password_login);
        btn = bindView(R.id.btn_login);
        warnId = bindView(R.id.warn_id);
        warnPassWord = bindView(R.id.warn_password);
        back = bindView(R.id.back_login);
        idRegister = bindView(R.id.et_id_register);
        // 注册, 取消注册文字
        registerTv = bindView(R.id.code_login);
        // 注册按钮
        btnRegister = bindView(R.id.btn_register);
        setClick(this, cancel, back, idRegister, registerTv, btn, btnRegister);

    }

    @Override
    protected void initData() {

        //登录
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = id.getText().toString();
                mPassword = passWord.getText().toString();

                userLogin();
            }
        });

        //注册
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = id.getText().toString();
                mPassword = passWord.getText().toString();

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
                    BMOB_LOGIN = true;
                    if (BMOB_LOGIN && HUANXIN_LOGIN){
                        ToastUtil.showShortToast("登录成功");
                        Values.USER_NAME = mName;
                        finish();
                    }

                } else {
                    BMOB_LOGIN = false;
                    ToastUtil.showShortToast("登录失败,请重试");
                    Log.d("MainActivity", e.getMessage());
                }
            }
        });

        EMClient.getInstance().login(mName, mPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                HUANXIN_LOGIN = true;
                if (BMOB_LOGIN && HUANXIN_LOGIN){
//                    ToastUtil.showShortToast("登录成功");
                    Values.USER_NAME = mName;
                    finish();
                }
            }

            @Override
            public void onError(int i, String s) {
                HUANXIN_LOGIN = false;
                ToastUtil.showShortToast("登录失败,请重试");
                Log.d("ECLoginActivity", "登录失败" + i+ "   " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.d("1111111", "i:" + i + "s" + s);
            }
        });



    }


    //bmob注册
    private void userRegister() {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(mName);
        bmobUser.setPassword(mPassword);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    BMOB_REGISTER = true;
                    if (BMOB_REGISTER && HUANXIN_REGISTER) {
                        ToastUtil.showShortToast("注册成功");
                    }
                } else {
                    BMOB_REGISTER = false;
                    ToastUtil.showShortToast(e.getMessage() + "注册失败");
                    Log.d("ssssLoginActivity", e.getMessage());
                }
            }
        });

        // 环信注册
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(mName,mPassword);
                    HUANXIN_REGISTER = true;
                    if (BMOB_REGISTER && HUANXIN_REGISTER) {
                        ToastUtil.showShortToast("注册成功");
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    HUANXIN_REGISTER = false;
                    Log.d("ECLoginActivity", "注册失败" + e.getErrorCode() + "  " + e.getMessage());
                }

            }
        }).start();
    }


    @Override
    public void onClick(View v) {

    }
}
