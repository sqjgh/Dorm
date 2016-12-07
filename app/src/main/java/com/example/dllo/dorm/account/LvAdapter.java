package com.example.dllo.dorm.account;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.CommonVH;

import java.util.ArrayList;

/**
 * Created by zhaojun on 16/12/6.
 */
public class LvAdapter extends BaseAdapter {

    ArrayList<AccountBean> mArrayList;

    public void setArrayList(ArrayList<AccountBean> arrayList) {
        mArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mArrayList == null ? 0 : mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH viewholder = CommonVH.getViewHolder(convertView, parent, R.layout.account_lv_item);
        if (mArrayList == null) {
        } else {
            viewholder.setText(R.id.tv_account_item_useby, mArrayList.get(position).getUseBy())
                    .setText(R.id.tv_account_item_money, mArrayList.get(position).getUseMoney());
            if ("舍费收取".equals(mArrayList.get(position).getUseBy())) {
                viewholder.setTextColor(R.id.tv_account_item_useby, 0xFF53EC20);
                viewholder.setTextColor(R.id.tv_account_item_money, 0xFF53EC20);
            } else {
                viewholder.setTextColor(R.id.tv_account_item_useby, 0xFFED2D44);
                viewholder.setTextColor(R.id.tv_account_item_money, 0xFFED2D44);
            }
        }
        return viewholder.getItemView();
    }
}
