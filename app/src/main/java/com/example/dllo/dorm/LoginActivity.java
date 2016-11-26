package com.example.dllo.dorm;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dllo.dorm.base.BaseActivity;

/**
 * Created by Wanghuan on 16/11/26.
 */
public class LoginActivity extends BaseActivity{
    private EditText userId,userPassword;
    private Button login;
    private String name;
    private String password;
    @Override
    protected void initData() {
         name = userId.getText().toString();
        password = userPassword.getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initViews() {

        userId = bindView(R.id.id);
        userPassword = bindView(R.id.password);
        login = bindView(R.id.start_register);

    }

    @Override
    protected int getLayout() {
        return R.layout.register;
    }
}
