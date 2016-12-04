package com.example.dllo.dorm.account;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by zhaojun on 16/12/3.
 */

public class AccountActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_account;
    }

    @Override
    protected void initViews() {
        mViewPager = bindView(R.id.vp_account);
        mTabLayout = bindView(R.id.tb_account);
    }

    @Override
    protected void initData() {

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add("第" + i + "月份");
        }
        AccountAdapter accountAdapter = new AccountAdapter(getSupportFragmentManager(),arrayList);
        mViewPager.setAdapter(accountAdapter);
        mTabLayout.setTabTextColors(Color.BLACK,Color.RED);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);
        //tablayout滑动
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
