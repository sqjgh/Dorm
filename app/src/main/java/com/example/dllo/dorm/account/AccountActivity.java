package com.example.dllo.dorm.account;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.timeform.TimeUtil;

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
        int accountTimeYear = TimeUtil.getTimeYear();
        int accountTimeMone = TimeUtil.getTimeMone();
        int accountTimeDay = TimeUtil.getTimeDay();
        String accountTime = TimeUtil.getTime();
        String year = accountTime.substring(0,4);

        for (int j = 0; j < 1; j++) {  //1年的日历
            accountTimeYear += j;
            for (int i = 1; i < 13; i++) { //12个月
                accountTimeMone += 1;
                if (12 < accountTimeMone) {
                    accountTimeMone = 1;
                }

                for (int k = 1; k < 32; k++) {  //31天
                    String mTab = accountTimeYear + "年" + accountTimeMone + "月" + k + "日";
                    arrayList.add(mTab);
                }

            }
        }
        AccountAdapter accountAdapter = new AccountAdapter(getSupportFragmentManager(), arrayList);
        mViewPager.setAdapter(accountAdapter);
        mTabLayout.setTabTextColors(Color.WHITE, Color.RED);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);
        //tablayout滑动
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < 1000; i++) {
           String pageTitle = (String) accountAdapter.getPageTitle(i);
            String nowTime = TimeUtil.getTimeYear() + "年" + accountTimeMone + "月" + accountTimeDay + "日";
            if (nowTime.equals(pageTitle)){
                mViewPager.setCurrentItem(i,false);
               break;
            }

        }
    }
}
