package com.example.dllo.dorm.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.View;
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

    @Override
    protected void initData() {
        data = new ArrayList<>();
        View guideOneIV = this.getLayoutInflater().inflate(R.layout.guide_image_one, null);
        View guideTwoIV = this.getLayoutInflater().inflate(R.layout.guide_image_two, null);
        View guideThreeIV = this.getLayoutInflater().inflate(R.layout.guide_image_three, null);
        guideLayout = guideThreeIV.findViewById(R.id.aty_guide_layout);
        guideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        data.add(guideOneIV);
        data.add(guideTwoIV);
        data.add(guideThreeIV);


        mGuideViewPagerAdapter = new GuideViewPagerAdapter(data);
        mViewPager.setAdapter(mGuideViewPagerAdapter);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void initViews() {
        mViewPager = bindView(R.id.guide);

    }

    @Override
    protected int getLayout() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("first", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst", true);
        if (!isFirst) {

            startActivity(new Intent(this, WelcomeActivity.class));
            finish();


        }
            SharedPreferences mSharedPreferences = getSharedPreferences("first", MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
            return R.layout.guide;





    }
}