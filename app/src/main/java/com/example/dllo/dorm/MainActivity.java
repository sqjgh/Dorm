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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.dorm.account.AccountActivity;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.MyApp;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.express.ExpressActivity;
import com.example.dllo.dorm.firstpage.chat.ChatInfoActivity;
import com.example.dllo.dorm.firstpage.flingswipe.SwipeFlingAdapterView;
import com.example.dllo.dorm.firstpage.swipecards.CardAdapter;
import com.example.dllo.dorm.firstpage.swipecards.CardMode;
import com.example.dllo.dorm.game.game2048.GameActivity;
import com.example.dllo.dorm.setting.IDSettingActivity;
import com.example.dllo.dorm.setting.SetUpActivity;
import com.example.dllo.dorm.todayinhistory.HistoryActivity;
import com.example.dllo.dorm.tools.DataCleanManager;
import com.example.dllo.dorm.tools.okhttp.ContentBean;
import com.example.dllo.dorm.tools.okhttp.HttpUtil;
import com.example.dllo.dorm.tools.okhttp.ResponseCallBack;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.example.dllo.dorm.welcome.loginmvp.LoginMainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

//import com.example.dllo.dorm.firstpage.chat.SqjTestChat;


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

    @Override
    protected void initViews() {


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

    }

    private void initNickname() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.getObject(Values.OBJECT_ID, new QueryListener<MyUser>() {

            @Override
            public void done(MyUser object, BmobException e) {
                if(e==null){
                    //获得playerName的信息
                    Values.NICKNAME = object.getNickname();
                    if (object.getIcon()!= null){
                        Values.ICON = object.getIcon();
                        getIcon();
                    }
                    if (Values.USER_NAME.equals("")){
                        nicknameLeftSlide.setText("用户昵称");
                    }else {
                        nicknameLeftSlide.setText(Values.NICKNAME);
                        usernameLeftSlide.setText(Values.USER_NAME);
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });


    }


    private void getIcon(){












    }
    @Override
    protected void onResume() {
        super.onResume();
        // 获得群组ID
        GroupID();
        // 获得昵称
        if (!Values.OBJECT_ID.equals("")){
            initNickname();
            // 获得头像
            getIcon();
        }


        // 获得程序缓存
        showSize();
        cach.setText("清除缓存: \n" + mCacheSize);



    }




    private void myLogin(){
        Values.GROUP_ID = "";
        if (Values.USER_NAME != "") {
            // 退出环信账号
            EMClient.getInstance().logout(true, new EMCallBack() {
                @Override
                public void onSuccess() {
                    HUANXIN_OUT = true;
                    if (HUANXIN_OUT && BMOB_OUT) {
                        Values.USER_NAME = "";
                        Values.OBJECT_ID = "";
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
                    ToastUtil.showShortToast("退出成功");
                    Values.OBJECT_ID = "";
                    Values.USER_NAME = "";
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
                    if (groupList.size() > 0){
                        Values.GROUP_ID = groupList.get(0).getGroupId();
                        //根据群组ID从服务器获取群组基本信息
                        EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupList.get(0).getGroupId() + "");
                        Values.GROUP_MEMBERS = group.getMembers(); // 获取群成员
                        Values.GROUP_OWNER = group.getOwner();// 获取群主
                        Log.d("MainActivity11111", "Values.GROUP_MEMBERS:" + Values.GROUP_MEMBERS);
                        Log.d("MainActivity11111", Values.GROUP_OWNER);
                        if (Values.GROUP_OWNER == Values.USER_NAME){
                            Values.OWNER = true;
                        }
                    }else {
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
                    Log.d("Sysout", temp);
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
                // 聊天跳转
                initChat();
                break;
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
                // 个人中心
                Intent intent = new Intent(MainActivity.this, SetUpActivity.class);
                startActivity(intent);
                break;
            case R.id.left_test01:
                // 账号信息设置页面
                if (Values.USER_NAME.equals("")) {
                    Intent intent1 = new Intent(MainActivity.this, LoginMainActivity.class);
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(MainActivity.this, IDSettingActivity.class);
                    startActivity(intent2);
                }
                Toast.makeText(this, "个人设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test02:
                Toast.makeText(this, "恭喜您,这是全球同步最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test03:
                // 清除缓存
                cleanManager();
                Toast.makeText(this, "清除缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_test04:
                myLogin();
//                Toast.makeText(this, "登录/注销/切换账号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test01:
                Intent intent3 = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "搜搜", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_test02:
                Toast.makeText(this, "右侧测试02", Toast.LENGTH_SHORT).show();
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
                Log.d("yyyy", Thread.currentThread().getName());
                ToastUtil.showShortToast("lalalalala---");
            }
        }).start();
    }


    private void initChat() {
        if (Values.USER_NAME.equals("")){
            Intent intent = new Intent(MainActivity.this,LoginMainActivity.class);
            startActivity(intent);
        }else {
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
                    Log.d("Sysout", temp);
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
