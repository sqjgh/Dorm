package com.example.dllo.dorm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.firstpage.chat.ChatInfoActivity;
import com.example.dllo.dorm.firstpage.chat.ChoseActivity;
import com.example.dllo.dorm.firstpage.chat.LoginTestActivity;
import com.example.dllo.dorm.firstpage.chat.SqjTestChat;
import com.example.dllo.dorm.firstpage.flingswipe.SwipeFlingAdapterView;
import com.example.dllo.dorm.firstpage.swipecards.CardAdapter;
import com.example.dllo.dorm.firstpage.swipecards.CardMode;
import com.example.dllo.dorm.tools.okhttp.ContentBean;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;

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
    private ImageView chat;
    private TextView exitID;
    private int a;
    private int NORMAL_INTENT = 0;

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


    }



    @Override
    protected void initViews() {
        exitID = bindView(R.id.chat_exit_ID);
        unLike = bindView(R.id.unlike);
        like = bindView(R.id.like);
        chat = bindView(R.id.chat_test);
        mFloatingActionButton = bindView(R.id.main_chat);
        leftSlide = (ImageView) findViewById(R.id.left_slide);
        rightSlide = (ImageView) findViewById(R.id.right_slide);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        refresh= bindView(R.id.refresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        setClick(this,chat,exitID, unLike, like, mFloatingActionButton,leftSlide,rightSlide,refresh);

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
                    String str = "http://pic.qiushibaike.com/system/pictures/11805/" + id + "/medium/app" + item.getId() + ".webp";
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
                ToastUtil.showShortToast("点击图片");
            }
        });

    }


    //探探btn监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 注销环信账号
            case R.id.chat_exit_ID:
                EMClient.getInstance().logout(true);
                break;
            // 聊天跳转
            case R.id.chat_test:
                //注册一个监听连接状态的listener
                EMClient.getInstance().addConnectionListener(new MyConnectionListener());
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
                ToastUtil.showShortToast("加载成功");
                getInterestingContent();
                break;
            case R.id.left_slide:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.right_slide:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.userInfo:

                Intent intent = new Intent(MainActivity.this,SetUpActivity.class);
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

    private void groupList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    List<EMGroup> groupList = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                    if (groupList.size() != 0){
                        Log.d("ChoseActivity", groupList.get(0).getGroupId());
                        String inputGroupID = groupList.get(0).getGroupId();
                        Intent intent1 = new Intent(MainActivity.this, SqjTestChat.class);
                        intent1.putExtra("groupID", inputGroupID);
                        startActivity(intent1);
                    }else {
                        Intent intent1 = new Intent(MainActivity.this, ChoseActivity.class);
                        startActivity(intent1);
                    }

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        a = NORMAL_INTENT;
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            // 账号登录了
            Log.d("MyConnectionListener", "连接成功");
            // 自动跳转此账号所在的群

            if (a == NORMAL_INTENT){ // 防止多次触发"连接成功"
                groupList();
            }
            a++;
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                        Log.d("MyConnectionListener", "账号已经被移除");
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){
                            // 账号没有登录
                            Log.d("MyConnectionListener", "连接不到聊天服务器");
                            Intent intent1 = new Intent(MainActivity.this, LoginTestActivity.class);
                            startActivity(intent1);
                        }

                        else{

                            Log.d("MyConnectionListener", "当前网络不可用，请检查网络设置");
                        }

                    }
                }
            });
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





}
