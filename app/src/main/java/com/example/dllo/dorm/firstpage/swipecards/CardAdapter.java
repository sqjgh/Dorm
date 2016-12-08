package com.example.dllo.dorm.firstpage.swipecards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.CommonVH;

import java.util.List;

/**
 * Created by zhaojun on 16/11/22.
 * //TODO 通用ViewHolder
 */
public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private List<CardMode> mCardList;

    public CardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCardList(List<CardMode> cardList) {
        mCardList = cardList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCardList != null ? mCardList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mCardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonVH viewHolder= CommonVH.getViewHolder(convertView,parent,R.layout.card_item);

        Glide.with(mContext)
                .load(mCardList.get(position).getImages().get(0))
                .into((ImageView) viewHolder.getView(R.id.helloText));
        viewHolder.setText(R.id.card_name,mCardList.get(position).getName());

        return viewHolder.getItemView();
    }

}
