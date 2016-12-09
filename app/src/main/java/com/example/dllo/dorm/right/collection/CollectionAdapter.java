package com.example.dllo.dorm.right.collection;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.CommonVH;

import java.util.ArrayList;

/**
 * Created by zhaojun on 16/12/8.
 */
public class CollectionAdapter extends BaseAdapter {

    ArrayList<CollectionBean> mArrayList;

    public void setArrayList(ArrayList<CollectionBean> arrayList) {
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
        CommonVH viewHolder = CommonVH.getViewHolder(convertView, parent, R.layout.shape_item);
        viewHolder.setText(R.id.tv_shape_content, mArrayList.get(position).getContent())
                .glideSetImage(parent, R.id.iv_shape_pic, mArrayList.get(position).getCollectionUrl());
        return viewHolder.getItemView();
    }
}
