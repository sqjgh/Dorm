package com.example.dllo.dorm.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
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
    private PopupWindow mPopupWindow;

    public static AccountSameFragment getInstence(int position) {
        Log.d("aaaaa", "position:" + position);
        AccountSameFragment accountSameFragment = new AccountSameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY,position);
        accountSameFragment.setArguments(bundle);
        return accountSameFragment;
    }

    @Override
    protected void initData() {
        mPopupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
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
//        total.setText(pos);
        Log.d("aaaaa", "pos:" + pos);
    }

    @Override
    public void onClick(View v) {
        if (mPopupWindow.isShowing()) {

        }else {
            //设置动画
            mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
            View view = LayoutInflater.from(context).inflate(R.layout.add_account_pop,null);
            mPopupWindow.setContentView(view);
            mPopupWindow.showAsDropDown(addAccount,0,-400);
        }
    }
}
