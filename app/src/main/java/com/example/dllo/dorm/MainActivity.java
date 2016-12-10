package com.example.dllo.dorm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.MyApp;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.left.IDSettingActivity;
import com.example.dllo.dorm.left.loginmvp.LoginMainActivity;
import com.example.dllo.dorm.mainpage.chat.ChatInfoActivity;
import com.example.dllo.dorm.mainpage.flingswipe.SwipeFlingAdapterView;
import com.example.dllo.dorm.mainpage.swipecards.CardAdapter;
import com.example.dllo.dorm.mainpage.swipecards.CardMode;
import com.example.dllo.dorm.right.account.AccountActivity;
import com.example.dllo.dorm.right.collection.CollectionActivity;
import com.example.dllo.dorm.right.collection.CollectionBean;
import com.example.dllo.dorm.right.express.ExpressActivity;
import com.example.dllo.dorm.right.game2048.GameActivity;
import com.example.dllo.dorm.right.news.HistoryActivity;
import com.example.dllo.dorm.right.weather.WeatherActivity;
import com.example.dllo.dorm.tools.DataCleanManager;
import com.example.dllo.dorm.tools.okhttp.ContentBean;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView setLogin;

    private LinearLayout mLinearLayout;
    private TextView mTextView;
    private String mCacheSize;
    private boolean BMOB_OUT = false;
    private boolean HUANXIN_OUT = false;
    private LinearLayout setupMy;

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

    private TextView nicknameLeftSlide;
    private TextView usernameLeftSlide;
    private TextView cach;
    private ImageView icon;
    private int a;

    private ArrayList<CollectionBean> collectionList = new ArrayList<>();
    private int mNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        icon = bindView(R.id.icon_left_slide);
        cach = bindView(R.id.left_test03); // 缓存
        unLike = bindView(R.id.unlike);
        like = bindView(R.id.like);
        usernameLeftSlide = bindView(R.id.username_left_slide);
        nicknameLeftSlide = bindView(R.id.nickname_left_slide);
        mFloatingActionButton = bindView(R.id.main_chat);
        leftSlide = bindView(R.id.left_slide);
        rightSlide = bindView(R.id.right_slide);
        refresh = bindView(R.id.refresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_slide);
        setClick(this, unLike, like, mFloatingActionButton, leftSlide, rightSlide, refresh);

    }

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

    private void initNickname() {
        if (!Values.OBJECT_ID.equals("")) {
            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
            query.getObject(Values.OBJECT_ID, new QueryListener<MyUser>() {
                @Override
                public void done(MyUser object, BmobException e) {
                    if (e == null) {
                        //获得昵称
                        if (object.getNickname() != null) {
                            Values.NICKNAME = object.getNickname();
                        } else {
                            Values.NICKNAME = "";
                        }


                        Values.USER_NAME = object.getUsername();
                        if (object.getIcon() != null) {
                            Values.ICON_URL = object.getIcon();
                            // 获取头像
                            getIcon();
                        } else {
                            icon.setImageResource(R.mipmap.twopeople);
                        }
                        if (Values.NICKNAME.equals("")) {
                            nicknameLeftSlide.setText("用户昵称");
                            usernameLeftSlide.setText(Values.USER_NAME);
                        } else {
                            nicknameLeftSlide.setText(Values.NICKNAME);
                            usernameLeftSlide.setText(Values.USER_NAME);
                        }
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }

                }

            });
        } else {
            usernameLeftSlide.setText("用户昵称");
            nicknameLeftSlide.setText("用户账号");
        }

    }


    private void getIcon() {
        Log.d("查看值", Values.ICON_URL);
        if (!Values.ICON_URL.equals("")) {
            Glide.with(this)
                    .load(Values.ICON_URL)
                    .into(icon);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        a = 0;
        // 获得群组ID
        GroupID();
        // 获得昵称 / 头像
        if (!Values.OBJECT_ID.equals("")) {
            initNickname();
        } else {
            icon.setImageResource(R.mipmap.twopeople);
            nicknameLeftSlide.setText("用户昵称");
            usernameLeftSlide.setText("用户账号");
        }
        // 获得程序缓存
        showSize();
        cach.setText("清除缓存: \n" + mCacheSize);


    }


    private void myLogin() {
        HUANXIN_OUT = false;
        BMOB_OUT = false;
        if (!Values.USER_NAME.equals("")) {
            Values.GROUP_ID = "";
            Values.USER_NAME = "";
            Values.OBJECT_ID = "";
            Values.ICON_URL = "";
            Values.NICKNAME = "";
            // 退出环信账号
            EMClient.getInstance().logout(true, new EMCallBack() {
                @Override
                public void onSuccess() {
                    HUANXIN_OUT = true;
                    if (HUANXIN_OUT && BMOB_OUT) {
                        Values.USER_NAME = "";
                        Values.OBJECT_ID = "";
                        Values.ICON_URL = "";
                        Values.NICKNAME = "";
                        Values.GROUP_ID = "";
                        Intent intent = new Intent(MainActivity.this, LoginMainActivity.class);
                        startActivity(intent);
                        ToastUtil.showShortToast("退出成功");
                    }
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    Log.d("SetUpActivity", "登出账号失败, 请重试");
                }
            });

            // 退出Bmob账号
            MyUser.logOut();
            BmobUser currentUser = BmobUser.getCurrentUser();
            if (currentUser == null) {
                BMOB_OUT = true;
                if (BMOB_OUT && HUANXIN_OUT) {
//                    ToastUtil.showShortToast("退出成功");
                    Values.OBJECT_ID = "";
                    Values.USER_NAME = "";
                    Values.ICON_URL = "";
                    Values.NICKNAME = "";
                    Values.GROUP_ID = "";
                    Intent intent = new Intent(MainActivity.this, LoginMainActivity.class);
                    startActivity(intent);
                }


            } else {
                Log.d("SetUpActivity", "登出账号失败");
            }

        } else {
            Intent intent = new Intent(MainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }

    }


    private void initSlide() {
    }

    /**
     * 获得当前账号群组信息
     */
    public void GroupID() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    List<EMGroup> groupList = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    if (groupList.size() > 0) {
                        Values.GROUP_ID = groupList.get(0).getGroupId();
                        //根据群组ID从服务器获取群组基本信息
                        EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupList.get(0).getGroupId() + "");
                        Values.GROUP_MEMBERS = group.getMembers(); // 获取群成员
                        Log.d("LoginModel333", "Values.GROUP_MEMBERS:" + Values.GROUP_MEMBERS);
                        Log.d("LoginModel333", Values.GROUP_OWNER);
                        Values.GROUP_OWNER = group.getOwner();// 获取群主
                        if (Values.GROUP_OWNER == Values.USER_NAME) {
                            Values.OWNER = true;
                        }
                    } else {
                        Values.GROUP_MEMBERS = null; // 群成员清空
                        Values.GROUP_OWNER = "";// 群主清空
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void cycleAddUrls() {

        for (int i = 0; i < 50; i++) {
            List<String> s = new ArrayList<>();
            s.add(Values.TT_IMAFE_URL_DEFAULT);
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
                Log.d("MainActivity-----", dataObject.getClass().getSimpleName());
            }

            @Override  //右滑监听
            public void onRightCardExit(Object dataObject) {

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


        //点击图片收藏
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                itemPosition += 1;
                String content = collectionList.get(itemPosition).getContent();
                String shapeUrl = collectionList.get(itemPosition).getCollectionUrl();
            }
        });
    }

    @Subscribe
    public void getShapeContent(CollectionBean event) {

        collectionList.add(event);
        mNum = event.getNum();

    }


    private void showSize() {
        try {
            mCacheSize = DataCleanManager.getCacheSize(MyApp.getContext().getCacheDir());
            Log.d("SetUpActivity----", mCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanManager() {
        DataCleanManager.cleanInternalCache(MainActivity.this);

    }

    //探探btn监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册一个监听连接状态的listener
//                EMClient.getInstance().addConnectionListener(new MyConnectionListener());
            case R.id.unlike:
                imgMoveToLeft();
                break;
            case R.id.like:
                imgMoveToRight();
                break;
            case R.id.main_chat:
                // Toast.makeText(this, "聊天", Toast.LENGTH_SHORT).show();
                initChat();
            case R.id.refresh:
                //暂时不用
                break;
            case R.id.left_slide:
                // 左侧菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.right_slide:
                // 右侧菜单
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.userInfo:
                // 刷新头像
                getIcon();
                break;
            case R.id.left_mysetting:
                // 账号信息设置页面
                if (Values.USER_NAME.equals("")) {
                    Intent intent1 = new Intent(MainActivity.this, LoginMainActivity.class);
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(MainActivity.this, IDSettingActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.left_update:
                Toast.makeText(this, "恭喜您,当前全球同步最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test03:
                // 清除缓存
                cleanManager();
                Toast.makeText(this, "清除缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test04:
                if (a == 0) {
                    ++a;
                    myLogin();
                }
//                Toast.makeText(this, "登录/注销/切换账号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_search:
                Intent intent3 = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "搜搜", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_weather:
                Intent intent4 = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "看天气撒", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_express:

                Intent intentExpress = new Intent(MainActivity.this, ExpressActivity.class);
                startActivity(intentExpress);
                Toast.makeText(this, "查快递啦", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_2048:
                Toast.makeText(this, "欢迎进入 2048 @_@", Toast.LENGTH_SHORT).show();
                Intent intentGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intentGame);
                break;
            case R.id.right_mybook:
                Toast.makeText(this, "记账本", Toast.LENGTH_SHORT).show();
                Intent intentAccount = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intentAccount);
                break;
            case R.id.right_like:
                // 收藏
                Intent intentShape = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(intentShape);
                break;
        }
    }



    private void initChat() {

            if (Values.USER_NAME.equals("")) {
                Intent intent = new Intent(MainActivity.this, LoginMainActivity.class);
                startActivity(intent);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, mFloatingActionButton, mFloatingActionButton.getTransitionName());
                    startActivity(new Intent(this, ChatInfoActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, ChatInfoActivity.class));
                }
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
