package com.example.dllo.dorm;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.firstpage.flingswipe.SwipeFlingAdapterView;
import com.example.dllo.dorm.firstpage.swipecards.CardAdapter;
import com.example.dllo.dorm.firstpage.swipecards.CardMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<CardMode> al;
    private CardAdapter adapter;
    private int i;
    private SwipeFlingAdapterView flingContainer;
    private List<List<String>> list = new ArrayList<>();
    private ImageView unLike, like, chat;
    private ImageView leftSlide;
    private ImageView rightSlide;
    private DrawerLayout drawerLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        // 探探添加数据
        cycleAddUrls();
        // 左右导航栏
        initSlide();


    }

    private void initSlide() {

    }

    @Override
    protected void initViews() {
        unLike = bindView(R.id.unlike);
        like = bindView(R.id.like);
        chat = bindView(R.id.chat);
        leftSlide = (ImageView) findViewById(R.id.left_slide);
        rightSlide = (ImageView) findViewById(R.id.right_slide);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        setClick(this, unLike, like, chat,leftSlide,rightSlide);
    }


    private void cycleAddUrls() {
        al = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            List<String> s = new ArrayList<>();
            s.add(imageUrls[i]);
            list.add(s);
        }
        /***********************/
        al.add(new CardMode("段子1", 21, list.get(0)));
        al.add(new CardMode("段子2", 21, list.get(1)));
        al.add(new CardMode("段子3", 21, list.get(2)));
        al.add(new CardMode("段子4", 21, list.get(3)));
        al.add(new CardMode("段子5", 21, list.get(4)));
        al.add(new CardMode("段子6", 21, list.get(5)));
        al.add(new CardMode("段子7", 21, list.get(6)));
        al.add(new CardMode("段子8", 21, list.get(7)));
        al.add(new CardMode("段子9", 21, list.get(8)));
        al.add(new CardMode("段子10", 21, list.get(9)));
        al.add(new CardMode("段子11", 21, list.get(10)));
        al.add(new CardMode("段子12", 21, list.get(11)));
        al.add(new CardMode("段子13", 21, list.get(12)));
        al.add(new CardMode("段子14", 21, list.get(13)));
        al.add(new CardMode("段子15", 21, list.get(14)));
        /**************************/
        adapter = new CardAdapter(this, al);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(adapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                al.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(MainActivity.this, "不喜欢");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(MainActivity.this, "喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                al.add(new CardMode("没数据的时候", 1, list.get(itemsInAdapter % imageUrls.length - 1)));
                adapter.notifyDataSetChanged();
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = flingContainer.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MainActivity.this, "点击图片");
            }
        });

    }

    //探探btn监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unlike:
                imgMoveToLeft();
                break;
            case R.id.like:
                imgMoveToRight();
                break;
            case R.id.chat:
                Toast.makeText(this, "这里跳转一个framgent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_slide:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.right_slide:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.userInfo:
                Toast.makeText(this, "个人中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test01:
                Toast.makeText(this, "测试01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test02:
                Toast.makeText(this, "测试02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test03:
                Toast.makeText(this, "测试03", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test04:
                Toast.makeText(this, "测试04", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test01:
                Toast.makeText(this, "右侧测试01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test02:
                Toast.makeText(this, "右侧测试02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test03:
                Toast.makeText(this, "右侧测试03", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test04:
                Toast.makeText(this, "右侧测试04", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test05:
                Toast.makeText(this, "右侧测试05", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //toast方法  没啥用 直接打toast也行
    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    //最上面的图片左划切换
    public void imgMoveToLeft() {
        flingContainer.getTopCardListener().selectLeft();
    }

    //最上面的图片右划切换
    public void imgMoveToRight() {
        flingContainer.getTopCardListener().selectRight();
    }


    public final String[] imageUrls = new String[]{
            "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037234_6318.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037194_2965.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037193_1687.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037193_1286.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037192_8379.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037178_9374.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037177_1254.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037177_6203.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037152_6352.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037151_9565.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037151_7904.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037148_7104.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037129_8825.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037128_3531.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037127_1085.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037094_8001.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037093_7168.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037091_4950.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949643_6410.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949642_6939.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949630_4505.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949630_4593.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949629_7309.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949629_8247.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949615_1986.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_8482.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_3743.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_4199.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949599_3416.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949599_5269.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949598_7858.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949598_9982.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949578_2770.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949578_8744.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949577_5210.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949577_1998.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949482_8813.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949481_6577.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949480_4490.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949455_6792.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949455_6345.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949442_4553.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949441_8987.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949441_5454.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949454_6367.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949442_4562.jpg"
    };


}
