package com.example.dllo.dorm.tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by wanghuan on 16/11/31.
 * 通用的 ViewHolder
 */

public class CommonVH extends RecyclerView.ViewHolder {
    // SparseArray 用法和HashMap一样
    // 但是key 固定是int类型
    // 用它来存放所有的View, key就是View的id
    private SparseArray<View> views;
    private View itemView; // 行布局

    public CommonVH(View itemView) {
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<>();
    }

    /**
     * 通过View的id来获得指定View
     * 如果该View没有赋值, 就先执行findViewById
     * 然后把它放到View的集合里
     * 使用泛型 来取消强转
     *
     * @param id
     * @return 指定View
     */
    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            // 证明SparseArray里没有这个View
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    // 返回行布局
    public View getItemView() {
        return itemView;
    }

    // 专门给ListView使用的方法
    public static CommonVH getViewHolder(View itemView, ViewGroup viewGroup, int itemId) {
        CommonVH viewHolder;
        if (itemView == null) {
            Context context = viewGroup.getContext();
            itemView = LayoutInflater.from(context).inflate(itemId, viewGroup, false);
            viewHolder = new CommonVH(itemView);
            itemView.setTag(viewHolder);
        } else {
            viewHolder = (CommonVH) itemView.getTag();
        }
        return viewHolder;

    }


    // 专门给recyclerview 使用的方法
    public static CommonVH getViewHolder(ViewGroup parent, int itemId) {

        return getViewHolder(null, parent, itemId);
    }

    /*********
     * ViewHolder 设置数据的方法
     ***********/
    // 设置文字
    public CommonVH setText(int id, String text) {
        TextView textView = (TextView) getView(id);
        textView.setText(text);
        return this;
    }


    // 设置图片

    public CommonVH setImage(int id, int imgId) {
        ImageView imageView = getView(id);
        imageView.setImageResource(imgId);
        return this;


    }


    public CommonVH setViewClick(int id, View.OnClickListener listener) {

        getView(id).setOnClickListener(listener);
        return this;
    }

    public CommonVH setItemClick(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }
}

