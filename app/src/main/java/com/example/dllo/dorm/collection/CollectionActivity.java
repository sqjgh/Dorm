package com.example.dllo.dorm.collection;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by zhaojun on 16/12/8.
 */
public class CollectionActivity extends BaseActivity implements View.OnClickListener {

    private ListView shapeLv;
    private TextView shapeBack;

    @Override
    protected int getLayout() {
        return R.layout.activity_shape;
    }

    @Override
    protected void initViews() {
        shapeLv = bindView(R.id.lv_shape);
        shapeBack = bindView(R.id.tv_shape_back);
        setClick(this, shapeBack);
    }

    @Override
    protected void initData() {
        CollectionAdapter collectionAdapter = new CollectionAdapter();

        ArrayList<CollectionBean> arrayList = new ArrayList<>();
        for (int i = 0; i < url.length; i++) {
            CollectionBean bean = new CollectionBean();
            bean.setContent(str[i]);
            bean.setCollectionUrl(url[i]);
            arrayList.add(bean);
        }
        collectionAdapter.setArrayList(arrayList);
        shapeLv.setAdapter(collectionAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shape_back:
                onBackPressed();
                break;
        }
    }

    String str[] = new String[]{
            "女人是水做的，要可爱，不能乱发脾气，其实我也是水做的，不过我是可乐，敢晃我一下你试试(•́へ•́ ╬)哼！",
            "班主任一学期的战果。",
    };
    String url[] = new String[]{
            "http://pic.qiushibaike.com/system/pictures/11814/118142323/medium/app118142323.webp",
            "http://pic.qiushibaike.com/system/pictures/11814/118143387/medium/app118143387.webp"
    };
}
