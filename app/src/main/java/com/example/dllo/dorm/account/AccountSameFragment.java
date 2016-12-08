package com.example.dllo.dorm.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseFragment;
import com.example.dllo.dorm.base.Values;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


/**
 * Created by zhaojun on 16/12/3.
 */
public class AccountSameFragment extends BaseFragment implements View.OnClickListener {

    private static final String KEY = "pos";

    private TextView total;
    private Button addAccount;
    private ListView lvAccount;
    private TextView itemCount;
    private ArrayList<AccountBean> mArrayList;
    private AccountBean mBean;
    private TextView showMoneyIn;
    private TextView showMoneyOut;
    private Button takeUpMoney;
    private LvAdapter adapter;

    public static AccountSameFragment getInstence(int position) {
        AccountSameFragment accountSameFragment = new AccountSameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, position);
        accountSameFragment.setArguments(bundle);
        return accountSameFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_same_account;
    }

    @Override
    protected void initView() {
        itemCount = bindView(R.id.tv_account_item_count);
        total = bindView(R.id.tv_total_account);
        showMoneyOut = bindView(R.id.tv_show_out_money);
        showMoneyIn = bindView(R.id.tv_show_in_money);
        addAccount = bindView(R.id.add_new_account);
        lvAccount = bindView(R.id.lv_account_content);
    }

    @Override
    protected void initData() {

        adapter = new LvAdapter();

        //TODO  此处xxx应该是月收入和支出  均应从bmob获取  0.00应该是两个的差 大于零绿色 小于零红色
        showMoneyIn.setText("0.00");
        showMoneyOut.setText("0.00");
        total.setText("0.00");

        setClick(this, addAccount, itemCount);

        //TODO 此处需要bmob获取一些数据


        mArrayList = new ArrayList<>();
        if (mBean != null) {
            mArrayList.add(mBean);
        }
        itemCount.setText(mArrayList.size() + "条");


        if (mArrayList == null) {
            //TODO  此处应该查询bmob获取导数据显示
        } else {

            lvAccount.setVisibility(View.VISIBLE);
            adapter.setArrayList(mArrayList);
            lvAccount.setAdapter(adapter);
        }
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
        Intent intent = new Intent(context, AddAccountActivity.class);
        startActivity(intent);
    }


    @Subscribe
    public void getAddAccountItem(AccountBean accountBean) {


        lvAccount.setVisibility(View.VISIBLE);
        mArrayList.add(accountBean);
        itemCount.setText(mArrayList.size() + "条");
        adapter.setArrayList(mArrayList);
        adapter.notifyDataSetChanged();
        lvAccount.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        int inMoney = 0;
        int outMoney = 0;

        for (int i = 0; i < mArrayList.size(); i++) {
            String useBy = mArrayList.get(i).getUseBy();

            if (Values.ACCOUNT_TEXT_CHICKED.equals(useBy)) {
                int useMoney = Integer.valueOf(mArrayList.get(i).getUseMoney());
                inMoney+=useMoney;
                showMoneyIn.setText(String.valueOf(inMoney));
            } else {
                int useMoney = Integer.valueOf(mArrayList.get(i).getUseMoney());
                outMoney+=useMoney;
                showMoneyOut.setText(String.valueOf(outMoney));
            }
        }

        int totalMoney = inMoney - outMoney;
        if (totalMoney>0){
            total.setTextColor(0xFF53EC20);
        }else {
            total.setTextColor(0xFFED2D44);
        }
        total.setText(String.valueOf(totalMoney));

    }
}
