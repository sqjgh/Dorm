package com.example.dllo.dorm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.bmob.v3.BmobUser;

/**
 * Created by Wanghuan on 16/11/26.
 */
public class SetUpActivity extends BaseActivity implements View.OnClickListener {
    private ImageView setIcon, setName, setMy, setNetWork, setUp;
    private TextView setLogin;

//    private String historyPassword = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //EventBus注册
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

        //尝试自动登录
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            ToastUtil.showShortToast("尝试自动登录");
            String username = bmobUser.getUsername();
            Values.USER_NAME = username;
        } else {
//            bmobUser = new BmobUser();
//            bmobUser.setUsername(Values.USER_NAME);
//            bmobUser.setPassword(historyPassword);
//            bmobUser.login(new SaveListener<BmobUser>() {
//                @Override
//                public void done(BmobUser bmobUser, BmobException e) {
//                    if (e == null) {
//                        ToastUtil.showShortToast("登录成功");
//                    } else {
//                        Toast.makeText(SetUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("MainActivity", e.getMessage());
//                    }
//                }
//            });
        }

    }

    @Override
    protected void initViews() {

        setLogin = bindView(R.id.my_login);
        setLogin.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {


        return R.layout.activity_setup;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_login:
                Intent intent = new Intent(SetUpActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN , sticky = true)
    public void getEventContent (EventContent event){  //切记 这里一定是public  不然找不到
        String userName =event.getUserName();
        String userPassword = event.getUserPassword();
        Values.USER_NAME = userName;
//        historyPassword = userPassword;
        setLogin.setText(Values.USER_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
