package com.example.dllo.dorm.setting.loginmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dllo on 16/11/22.
 */

public class LoginMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginFragment loginFragment = new LoginFragment();
        LoginModel loginModel = new LoginModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginFragment,loginModel);
        loginFragment.setPresenter(loginPresenter);
        loginModel.setPresenter(loginPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content,loginFragment)
                .commit();


    }

}
