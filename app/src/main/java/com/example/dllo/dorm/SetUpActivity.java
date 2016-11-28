package com.example.dllo.dorm;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.dorm.base.BaseActivity;

/**
 * Created by Wanghuan on 16/11/26.
 */
public class SetUpActivity  extends BaseActivity implements View.OnClickListener {
    private ImageView setIcon,setName,setMy,setNetWork,setUp;
    private TextView setLogin;
    @Override
    protected void initData() {

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
        switch (v.getId()){
            case R.id.my_login:
                Intent intent = new Intent(SetUpActivity.this,LoginActivity.class);
                startActivity(intent);

                break;

        }


    }
}
