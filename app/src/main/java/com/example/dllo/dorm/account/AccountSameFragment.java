package com.example.dllo.dorm.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseFragment;


/**
 * Created by zhaojun on 16/12/3.
 */
public class AccountSameFragment extends BaseFragment implements View.OnClickListener {

    private static final String KEY = "pos";

    private TextView total;
    private Button addAccount;


    public static AccountSameFragment getInstence(int position) {

        AccountSameFragment accountSameFragment = new AccountSameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY,position);
        accountSameFragment.setArguments(bundle);
        return accountSameFragment;
    }

    @Override
    protected void initData() {
        setClick(this,addAccount);
    }

    @Override
    protected void initView() {
        total = bindView(R.id.tv_total_account);
        addAccount = bindView(R.id.add_new_account);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_same_account;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //拿值
        Bundle argiment = getArguments();
        int pos = argiment.getInt(KEY);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context,AddAccountActivity.class);
        startActivity(intent);
    }


}
