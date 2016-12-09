package com.example.dllo.dorm.right.game2048;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.CommonVH;
import com.example.dllo.dorm.tools.timeform.TimeUtil;

import java.util.ArrayList;

/**
 * Created by zhaojun on 16/12/2.
 */
public class ChartAdapter extends BaseAdapter {

    ArrayList<Integer> mArrayList;

    public void setArrayList(ArrayList<Integer> arrayList) {
        mArrayList = arrayList;
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
        CommonVH viewHolder = CommonVH.getViewHolder(convertView, parent, R.layout.chart_item);
        String time = TimeUtil.getTime();
        viewHolder.setText(R.id.player_tiem, time)
                .setText(R.id.player_chart, String.valueOf(mArrayList.get(position)))
                .setText(R.id.player_ranking, "第" + (position + 1) + "名");
        return viewHolder.getItemView();
    }
}
