package com.example.dllo.dorm;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.firstpage.flingswipe.SwipeFlingAdapterView;
import com.example.dllo.dorm.firstpage.im.ChatInfoActivity;
import com.example.dllo.dorm.firstpage.swipecards.CardAdapter;
import com.example.dllo.dorm.firstpage.swipecards.CardMode;
import com.example.dllo.dorm.tools.okhttp.ContentBean;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<CardMode> al;
    private ArrayList<String> mImageUrls;
    private ArrayList<String> mContents;
    private int i;
    private SwipeFlingAdapterView flingContainer;
    private List<List<String>> list = new ArrayList<>();
    private ImageView unLike, like;
    private ImageView leftSlide;
    private ImageView rightSlide;
    private DrawerLayout drawerLayout;
    private FloatingActionButton mFloatingActionButton;

    private CardAdapter adapter;
    private ImageView refresh;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        mImageUrls = new ArrayList<>();
        mContents = new ArrayList<>();

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

        mFloatingActionButton = bindView(R.id.main_chat);
        leftSlide = (ImageView) findViewById(R.id.left_slide);
        rightSlide = (ImageView) findViewById(R.id.right_slide);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        refresh= bindView(R.id.refresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        setClick(this, unLike, like, mFloatingActionButton,leftSlide,rightSlide,refresh);

    }


    private void cycleAddUrls() {

        for (int i = 0; i < 50; i++) {
            List<String> s = new ArrayList<>();
            s.add("http://cdnq.duitang.com/uploads/item/201505/06/20150506144122_uvGVP.thumb.700_0.jpeg");
            list.add(s);
        }


        al = new ArrayList<>();

        HttpUtil.getContent("1", new ResponseCallBack<ContentBean>() {
            @Override
            public void OnResponse(ContentBean contentBean) {

                List<ContentBean.ItemsBean> items = contentBean.getItems();
                Log.d("yyyyy", "items.get(i).getId():" + items.get(i).getId());
                al.clear();

                for (ContentBean.ItemsBean item : items) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    int id = item.getId();
                    Log.d("ididididid", "id:" + id);
                    String str = "http://pic.qiushibaike.com/system/pictures/11805/" + id + "/medium/app" + item.getId() + ".webp";
                    Log.d("yyyyyyMainActivity", str);
                    arrayList.add(str);
                    al.add(new CardMode(item.getContent(), 1, arrayList));
                }
                adapter.setCardList(al);

            }

            @Override
            public void onError(Exception throwable) {

            }
        });

        adapter = new CardAdapter(MainActivity.this);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(adapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                al.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override  //左滑监听
            public void onLeftCardExit(Object dataObject) {
//                makeToast(MainActivity.this, "不喜欢");
            }

            @Override  //右滑监听
            public void onRightCardExit(Object dataObject) {
//                makeToast(MainActivity.this, "喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                al.add(new CardMode("请尝试刷新", 1, list.get(itemsInAdapter % 50)));
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
            case R.id.main_chat:
                Toast.makeText(this, "这里跳转一个framgent", Toast.LENGTH_SHORT).show();
                initChat();
            case R.id.refresh:
                Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
                getInterestingContent();
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

    private void initChat() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, mFloatingActionButton, mFloatingActionButton.getTransitionName());
            startActivity(new Intent(this, ChatInfoActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, ChatInfoActivity.class));
        }


    }

    //toast方法  没啥用 直接打toast也行
    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    //图片左划切换
    public void imgMoveToLeft() {
        flingContainer.getTopCardListener().selectLeft();
    }

    //图片右划切换
    public void imgMoveToRight() {
        flingContainer.getTopCardListener().selectRight();
    }



    //http的get请求
    private void getInterestingContent() {

        HttpUtil.getContent("1", new ResponseCallBack<ContentBean>() {
            @Override
            public void OnResponse(ContentBean contentBean) {
                Log.d("MainActivity", Thread.currentThread().getName());
                List<ContentBean.ItemsBean> items = contentBean.getItems();

                al.clear();

                for (ContentBean.ItemsBean item : items) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add("http://pic.qiushibaike.com/system/pictures/11805/" + item.getId() + "/medium/app" + item.getId() + ".webp");
                    al.add(new CardMode(item.getContent(), 1, arrayList));
                }
                adapter.setCardList(al);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception throwable) {

            }
        });

    }

    //这是个放图片的数组





}
