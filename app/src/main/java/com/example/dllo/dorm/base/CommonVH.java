package com.example.dllo.dorm.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by dllo on 16/10/31.
 */
public class CommonVH extends RecyclerView.ViewHolder {
    //SparseArray 用法和HashMap一样
    //但是key 固定是int类型
    //用它来存放所有View  key就是View的id
    private SparseArray<View> views;
    private View itemView; //行布局

    private CommonVH(View itemView) {
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            //证明SparseArray里没有View
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    //static 写成静态的才能用类名调用
    //专门给listview使用的方法
    public static CommonVH getViewHolder(View itemView, ViewGroup parent, int itemid) {
        CommonVH commonVH;
        if (itemView == null) {
            Context context = parent.getContext();
            itemView = LayoutInflater.from(context).inflate(itemid, parent, false);
            commonVH = new CommonVH(itemView);
            itemView.setTag(commonVH);
        } else {
            commonVH = (CommonVH) itemView.getTag();
        }
        return commonVH;
    }

    //重写一个recycleview专用方法  调用的时候参数少个view就不会写错了!
    public static CommonVH getViewHolder(ViewGroup parent, int itemId) {
        return getViewHolder(null, parent, itemId);
    }

    //返回行布局
    public View getItemView() {
        return itemView;
    }

    /******************************
     * ViewHolder设置数据的方法
     ************************/
    //设置文字
    public CommonVH setText(int id, String text) {
        TextView textView = getView(id);
        textView.setText(text);
        return this;
        //链式编程返回this是自己的类名  这样在调用这个方法的时候可以用"."连续调用
    }

    public CommonVH setImage(int id, int imgId) {
        ImageView imageView = getView(id);
        imageView.setImageResource(imgId);
        return this;
    }

    //Volleysingletion解析图片
//    public CommonVH setImage(int id, String url) {
//        ImageView imageView = getView(id);
//        VolleySingleton.getInstance().getImage(url, imageView);
//        return this;
//    }

    //Glide解析图片
    public CommonVH glideSetImage(ViewGroup parent,int id, String url) {
        ImageView imageView = getView(id);
        Context context = parent.getContext();
                Glide.with(context)
                .load(url)
                .into(imageView);
        return this;
    }


    public CommonVH setViewClick(int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
        return this;
    }

    //recyclerItem事件
    public CommonVH setItemClick(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

    public <T extends View> T bindView(View view, int id) {
        return (T) view.findViewById(id);
    }


    //设置圆头像
//    public void setCircleImg(int id, String url) {
//        ImageView imageView = getView(id);
//        VolleySingleton.getInstance().getCircleImg(url, imageView);
//    }

    //对item里的image设置点击事件
    public CommonVH setImgClick(int id, View.OnClickListener listener) {
        ImageView imageView = getView(id);
        imageView.setOnClickListener(listener);
        return this;
    }

    //对item里的text设置点击事件
    public CommonVH setTextClick(int id, View.OnClickListener listener) {
        TextView textview = getView(id);
        textview.setOnClickListener(listener);
        return this;
    }

    //设置item里面textview字体颜色
    public CommonVH setTextColor(int id, int color) {
        TextView textView = getView(id);
        textView.setTextColor(color);
        return this;
    }

}
