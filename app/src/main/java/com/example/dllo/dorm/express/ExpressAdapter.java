package com.example.dllo.dorm.express;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.tools.CommonVH;
import com.example.dllo.dorm.tools.okhttp.InfoBean;

import java.util.List;

/**
 * Created by Wanghuan on 16/12/1.
 */

public class ExpressAdapter extends BaseAdapter {


    private Context mContext;
    private InfoBean mInfoBeen;

    public ExpressAdapter(Context context) {
        mContext = context;
    }

    public void setInfoBeen(InfoBean infoBeen) {
        mInfoBeen = infoBeen;
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            count = mInfoBeen.getResult().size();
        }catch (NullPointerException e){
            count = 0;
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return mInfoBeen.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH commonVH = CommonVH.getViewHolder(convertView,parent, R.layout.info_express);
        commonVH.setText(R.id.info_express_time,mInfoBeen.getResult().get(position).getTime());
        commonVH.setText(R.id.info_express_info,mInfoBeen.getResult().get(position).getAction());


        return commonVH.getItemView();
    }
}
