package com.example.dllo.dorm.welcome;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import java.util.ArrayList;

/**
 * Created by Wanghuan on 16/11/24.
 */

public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private GuideViewPagerAdapter mGuideViewPagerAdapter;
    private ArrayList<View> data;
    private View guideLayout;
    public static boolean isForeground = false;
    private ImageView mImageView;

    private int margin = 0;

    public int getMargin() {
        return margin;
    }

    //用于属性动画的
    public void setMargin(int margin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        layoutParams.setMargins(0,-margin,0,margin - 3000);
        mImageView.setLayoutParams(layoutParams);
        this.margin = margin;
    }

    @Override
    protected void initData() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this,
                "margin",0,3000);
        objectAnimator.setDuration(15000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

    }

    @Override
    protected void onResume() {
//        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
//        isForeground = false;
        super.onPause();
    }

    @Override
    protected void initViews() {
//        mViewPager = bindView(R.id.guide);

        mImageView = bindView(R.id.grid_iv);
        mImageView.setEnabled(false);

    }

    @Override
    protected int getLayout() {
//        SharedPreferences sharedPreferences = this.getSharedPreferences("first", MODE_PRIVATE);
//        boolean isFirst = sharedPreferences.getBoolean("isFirst", true);
//        if (!isFirst) {
//
//            startActivity(new Intent(this, WelcomeActivity.class));
//            finish();
//
//
//        }
//            SharedPreferences mSharedPreferences = getSharedPreferences("first", MODE_PRIVATE);
//            SharedPreferences.Editor editor = mSharedPreferences.edit();
//            editor.putBoolean("isFirst", false);
//            editor.commit();
            return R.layout.guide;





    }
}