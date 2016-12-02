package com.example.dllo.dorm.firstpage.swipecards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dllo.dorm.R;

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
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.mCardImageView = (ImageView) convertView.findViewById(R.id.helloText);
            holder.mCardName = (TextView) convertView.findViewById(R.id.card_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext)
                .load(mCardList.get(position).getImages().get(0))
                .into(holder.mCardImageView);
        holder.mCardName.setText(mCardList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView mCardName;
        ImageView mCardImageView;
    }
}
