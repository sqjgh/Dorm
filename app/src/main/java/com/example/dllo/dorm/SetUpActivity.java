package com.example.dllo.dorm;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.MyApp;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.DataCleanManager;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.example.dllo.dorm.welcome.loginmvp.LoginMainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import cn.bmob.v3.BmobUser;


/**
 * Created by Wanghuan on 16/11/26.
 */
public class SetUpActivity extends BaseActivity implements View.OnClickListener {
    private ImageView setIcon, setName, setMy, setNetWork, setUp;
    private TextView setLogin;

    private LinearLayout mLinearLayout;
    private TextView mTextView;
    private String mCacheSize;

    private boolean BMOB_OUT = false;
    private boolean HUANXIN_OUT = false;


    @Override
    protected void initData() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获得程序缓存
        showSize();
        mTextView.setText(mCacheSize);
        //尝试自动登录
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            String username = bmobUser.getUsername();

        }

        if (Values.USER_NAME != "") {
            setLogin.setText(Values.USER_NAME);
        } else {
            setLogin.setText("点击登录");
        }

    }

    @Override
    protected void initViews() {


        mLinearLayout = bindView(R.id.setup_clean);
        setLogin = bindView(R.id.my_login);
        mTextView = bindView(R.id.setup_clean_tv);
        setLogin.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
        setLogin = bindView(R.id.my_login);

        setClick(this, setLogin);


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
                Values.GROUP_ID = "";
                setLogin.setText("点击登录");
                if (Values.USER_NAME != "") {
                    // 退出环信账号
                    EMClient.getInstance().logout(true, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            HUANXIN_OUT = true;
                            if (HUANXIN_OUT && BMOB_OUT) {
                                ToastUtil.showShortToast("登出账号成功");
                                Values.USER_NAME = "";
                                Intent intent = new Intent(SetUpActivity.this, LoginMainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(int code, String message) {
                            // TODO Auto-generated method stub
                            Log.d("SetUpActivity", "登出账号失败, 请重试");
                            ToastUtil.showShortToast("登出账号失败, 请重试");
                        }
                    });

                    // 退出Bmob账号
                    MyUser.logOut();
                    BmobUser currentUser = BmobUser.getCurrentUser();
                    if (currentUser == null) {
                        BMOB_OUT = true;
                        if (BMOB_OUT && HUANXIN_OUT) {
                            ToastUtil.showShortToast("登出账号成功");
                            Values.USER_NAME = "";
                            Intent intent = new Intent(SetUpActivity.this, LoginMainActivity.class);
                            startActivity(intent);
                        }


                    } else {
                        Log.d("SetUpActivity", "登出账号失败");
                        ToastUtil.showShortToast("登出账号失败, 请重试");
                    }

                } else {
                    Intent intent = new Intent(SetUpActivity.this, LoginMainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.setup_clean:
                // 清除缓存
                cleanManager();
                ToastUtil.showShortToast("清除缓存");
                break;

        }

    }



    private void showSize() {
        try {
            mCacheSize = DataCleanManager.getCacheSize(MyApp.getContext().getCacheDir());
            Log.d("SetUpActivity----", mCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanManager() {
        DataCleanManager.cleanInternalCache(SetUpActivity.this);

    }
}