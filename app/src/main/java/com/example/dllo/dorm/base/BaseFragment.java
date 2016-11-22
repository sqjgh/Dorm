package com.example.dllo.dorm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dllo.dorm.R;

/**
 * Created by dllo on 16/10/21.
 */
public abstract class BaseFragment extends Fragment{
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 如果是空布局就指定一个空布局
        if (getLayout() == 0){
            return inflater.inflate(R.layout.null_layout, container, false);
        }
        return inflater.inflate(getLayout(), container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected <T extends View> T bindView(int id){
        return (T) getView().findViewById(id);
    }
    // 指定在哪个View里 findViewBuId (例如popWindow)
    protected <T extends View> T bindView(View view, int id){
        return (T) view.findViewById(id);
    }
    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();
    // 设置监听事件 ... 可变数量的参数
    protected void setClick(View.OnClickListener clickListener,View ...views){
        for (View view :views){
            view.setOnClickListener(clickListener);
        }
    }

}
