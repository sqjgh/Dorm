package com.example.dllo.dorm.todayinhistory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.tools.CommonVH;

/**
 * Created by Wanghuan on 16/12/6.
 */

public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private HistoryBean mHistoryBean;

    public HistoryAdapter(Context context) {
        mContext = context;
    }

    public void setHistoryBean(HistoryBean historyBean) {
        mHistoryBean = historyBean;
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            count = mHistoryBean.getResult().size();
        }catch (NullPointerException e){
            count = 0;
        }
        return count;
    }
    @Override
    public Object getItem(int position) {
        return mHistoryBean.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH commonVH = CommonVH.getViewHolder(convertView,parent, R.layout.info_history);
        commonVH.setText(R.id.title_history,mHistoryBean.getResult().get(position).getFull_title());
       // commonVH.setImage(R.id.image_history,mHistoryBean.getResult().get(position).getImg());
        commonVH.setText(R.id.info_history,mHistoryBean.getResult().get(position).getContent());
        commonVH.setText(R.id.time_history,mHistoryBean.getResult().get(position).getPdate());
        commonVH.setText(R.id.from_history,mHistoryBean.getResult().get(position).getSrc());


        return commonVH.getItemView();
    }
}
