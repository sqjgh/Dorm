package com.example.dllo.dorm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.dorm.account.AccountActivity;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.express.ExpressActivity;
import com.example.dllo.dorm.firstpage.chat.ChatInfoActivity;
import com.example.dllo.dorm.firstpage.flingswipe.SwipeFlingAdapterView;

import com.example.dllo.dorm.firstpage.swipecards.CardAdapter;
import com.example.dllo.dorm.firstpage.swipecards.CardMode;

import com.example.dllo.dorm.game.game2048.GameActivity;
import com.example.dllo.dorm.todayinhistory.HistoryActivity;
import com.example.dllo.dorm.tools.okhttp.ContentBean;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.example.dllo.dorm.weather.WeatherActivity;


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
        leftSlide = bindView(R.id.left_slide);
        rightSlide = bindView(R.id.right_slide);
        refresh = bindView(R.id.refresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        setClick(this, unLike, like, mFloatingActionButton, leftSlide, rightSlide, refresh);

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

                al.clear();


                for (ContentBean.ItemsBean item : items) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    int id = item.getId();
                    String temp = String.valueOf(id).substring(0, 5);
                    String str = Values.TT_IMAGE_URL_FRONT + temp + "/" + item.getId() + Values.TT_IMAGE_URL_CENTRE + item.getId() + Values.TT_IMAGE_URL_LAST;

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

                ToastUtil.showShortToast("不喜欢");
            }

            @Override  //右滑监听
            public void onRightCardExit(Object dataObject) {

                ToastUtil.showShortToast("喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                //重新获取一下网络数据
                getInterestingContent();


                al.add(new CardMode("请刷新尝试", 1, list.get(itemsInAdapter % 50)));
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
                ToastUtil.showShortToast("点击图片事件");
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
                newToast();
                break;
            case R.id.left_slide:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.right_slide:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.userInfo:
                Intent intent = new Intent(MainActivity.this, SetUpActivity.class);
                startActivity(intent);
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
                Intent intent3 = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "搜搜", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test02:
                Intent intent4 = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "看天气撒", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test03:

                Intent intentExpress = new Intent(MainActivity.this, ExpressActivity.class);
                startActivity(intentExpress);
                Toast.makeText(this, "查快递啦", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test04:
                Toast.makeText(this, "欢迎进入 2048 @_@", Toast.LENGTH_SHORT).show();
                Intent intentGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intentGame);
                break;
            case R.id.right_test05:
                Toast.makeText(this, "记账本", Toast.LENGTH_SHORT).show();
                Intent intentAccount = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intentAccount);
                break;

        }
    }

    private void newToast() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ToastUtil.showShortToast("lalalalala---");
            }
        }).start();
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

    //图片左划切换
    public void imgMoveToLeft() {
        flingContainer.getTopCardListener().selectLeft();
    }

    //图片右划切换
    public void imgMoveToRight() {
        flingContainer.getTopCardListener().selectRight();
    }

    int pageNum = 1;

    //http的get请求
    private void getInterestingContent() {

        pageNum += 1;

        HttpUtil.getContent(String.valueOf(pageNum), new ResponseCallBack<ContentBean>() {
            @Override
            public void OnResponse(ContentBean contentBean) {
                List<ContentBean.ItemsBean> items = contentBean.getItems();

                al.clear();

                for (ContentBean.ItemsBean item : items) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String temp = String.valueOf(item.getId()).substring(0, 5);
                    arrayList.add(Values.TT_IMAGE_URL_FRONT + temp + "/" + item.getId() + Values.TT_IMAGE_URL_CENTRE + item.getId() + Values.TT_IMAGE_URL_LAST);
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



}
