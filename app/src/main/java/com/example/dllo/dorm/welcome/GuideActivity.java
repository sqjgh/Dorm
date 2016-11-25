package com.example.dllo.dorm.welcome;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
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
    private ScrollView mScrollView;

    private int margin = 0;

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        layoutParams.setMargins(0,margin,0,0);
        mImageView.setLayoutParams(layoutParams);
        this.margin = margin;
    }

    @Override
    protected void initData() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this,
                "margin",10,-3000);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

//        data = new ArrayList<>();
//        View guideOneIV = this.getLayoutInflater().inflate(R.layout.guide_image_one, null);
//        View guideTwoIV = this.getLayoutInflater().inflate(R.layout.guide_image_two, null);
//        View guideThreeIV = this.getLayoutInflater().inflate(R.layout.guide_image_three, null);
//        guideLayout = guideThreeIV.findViewById(R.id.aty_guide_layout);
//        guideLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(GuideActivity.this, WelcomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        data.add(guideOneIV);
//        data.add(guideTwoIV);
//        data.add(guideThreeIV);
//
//
//        mGuideViewPagerAdapter = new GuideViewPagerAdapter(data);
//        mViewPager.setAdapter(mGuideViewPagerAdapter);
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
        mScrollView = bindView(R.id.main_scroll);
        mImageView.setEnabled(false);
        mScrollView.setEnabled(false);
        mScrollView.setClickable(false);

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