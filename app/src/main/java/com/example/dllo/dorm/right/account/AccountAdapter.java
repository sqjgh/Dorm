package com.example.dllo.dorm.right.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by zhaojun on 16/12/3.
 */
public class AccountAdapter extends FragmentPagerAdapter {

    SparseArray<Fragment> fragments;
    ArrayList<String> date;

    public AccountAdapter(FragmentManager fm, ArrayList<String> date) {
        super(fm);
        fragments = new SparseArray<>();
        this.date = date;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.get(position) == null) {
            fragments.put(position, AccountSameFragment.getInstence(position));
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return date == null ? 0 : date.size();
    }

    public CharSequence getPageTitle(int position){
        return date.get(position);
    }

}
