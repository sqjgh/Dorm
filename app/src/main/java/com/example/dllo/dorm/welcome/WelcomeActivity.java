package com.example.dllo.dorm.welcome;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.dorm.MainActivity;
import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;

/**
 * Created by Wanghuan on 16/11/24.
 */
public class WelcomeActivity  extends BaseActivity{

    private TextView mTextView;

    @Override
    protected void initData() {
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.cancel();
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountDownTimer.cancel();
    }

    @Override
    protected void initViews() {
        mTextView = bindView(R.id.aty_wel_tv);

    }

    CountDownTimer mCountDownTimer = new CountDownTimer(5000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            mTextView.setText("欢迎");
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();

        }
    }.start();
    @Override
    protected int getLayout() {
        return R.layout.welcome;
    }

}
