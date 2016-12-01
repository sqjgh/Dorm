package com.example.dllo.dorm.firstpage.chat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by SongQingJun on 16/11/29.
 */

public class LoginTestActivity extends BaseActivity implements View.OnClickListener{

    private EditText mUsernameTV;
    private EditText mPasswordTV;
    private Button signUp;
    private Button signIn;

    @Override
    protected int getLayout() {
        return R.layout.test_chat_login;
    }

    @Override
    protected void initViews() {
        mUsernameTV = bindView(R.id.ec_edit_username);
        mPasswordTV = bindView(R.id.ec_edit_password);
        signUp = bindView(R.id.ec_btn_sign_up);
        signIn = bindView(R.id.ec_btn_sign_in);
        setClick(this, signUp, signIn);

    }

    @Override
    protected void initData() {



    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 注册
            case R.id.ec_btn_sign_up:
                signUp();
                break;
            // 登录
            case R.id.ec_btn_sign_in:
                signIn();
                break;

        }
    }

    /**
     * 注册方法
     */
    private void signUp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(mUsernameTV.getText().toString().trim(),mPasswordTV.getText().toString().trim());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.d("ECLoginActivity", "注册失败" + e.getErrorCode() + "  " + e.getMessage());
//                    "注册失败"
                }

            }
        }).start();
    }

    /**
     * 登录方法
     */
    private void signIn(){

        EMClient.getInstance().login(mUsernameTV.getText().toString().trim(), mPasswordTV.getText().toString().trim(), new EMCallBack() {
           @Override
            public void onSuccess() {
               ToastUtil.showShortToast("登录成功");
            }

            @Override
            public void onError(int i, String s) {

                Log.d("ECLoginActivity", "登录失败" + i+ "   " + s);
//                Toast.makeText(ECLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


}
