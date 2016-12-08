package com.example.dllo.dorm.firstpage.chat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.CommonVH;

import java.util.List;

/**
 * Created by SongQingJun on 16/12/8.
 */

public class ChatLVAdapter extends BaseAdapter{
    private List<String> mArrayList;
    private String owner;
    private String identity;

    public void setOwner(String owner) {
        this.owner = owner;
        notifyDataSetChanged();
    }

    public void setArrayList(List<String> arrayList) {
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
        CommonVH viewHolder = CommonVH.getViewHolder(convertView, parent, R.layout.item_dialog_chat);
        identity = null;
        if (mArrayList.get(position).equals(owner)){
            identity = "群主";
        }else {
            identity = "成员";
        }
        viewHolder.setText(R.id.idtv_item_dialog_chat, mArrayList.get(position))
                .setText(R.id.identitytv_item_dialog_chat, identity);
        return viewHolder.getItemView();
    }


}
