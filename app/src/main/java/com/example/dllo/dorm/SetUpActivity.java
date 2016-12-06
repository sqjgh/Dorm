package com.example.dllo.dorm;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.MyApp;
import com.example.dllo.dorm.tools.DataCleanManager;
import com.example.dllo.dorm.tools.toast.ToastUtil;

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

    @Override
    protected void initData() {

        //尝试自动登录
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            ToastUtil.showShortToast("尝试自动登录");
            String username = bmobUser.getUsername();

        }
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetUpActivity.this, "缓存大小", Toast.LENGTH_SHORT).show();
                mTextView.setText(mCacheSize);
            }
        });



    }

    @Override
    protected void initViews() {

        mLinearLayout = bindView(R.id.setup_clean);
        setLogin = bindView(R.id.my_login);
        mTextView = bindView(R.id.setup_clean);

        setLogin.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);

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
            case R.id.setup_clean:
                showSize();

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
