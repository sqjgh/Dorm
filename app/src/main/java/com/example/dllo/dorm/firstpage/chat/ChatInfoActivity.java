package com.example.dllo.dorm.firstpage.chat;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.dllo.dorm.MyUser;
import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Wanghuan on 16/11/24.
 */
public class ChatInfoActivity extends BaseActivity implements EMMessageListener, View.OnClickListener {
    private RelativeLayout otherRlContainer;
    private FloatingActionButton otherFabCircle;

    private LinearLayout chatMainLL;
    private RelativeLayout groupCreateRL;
    private Button createGroupBtn;


    private RecyclerView rvChat;
    private Button sendChat;
    private EditText etChat;
    private ImageView groupInformation;
    private ArrayList<ChatBean> arrayList;
    private ChatRVAdapter chatRVAdapter;
    private final int CHAT_SELF = 0;  // 自己发送的消息类型
    private final int CHAT_OTHERS = 1;  // 接收到的消息类型
    private ListView dialogLV;
    private ChatLVAdapter chatLVAdapter;
    private Button inventBtn;
    private EditText inventEt;
    private AlertDialog.Builder builder;
    private EMGroup group;
    private List<String> membersList;
    private String owner;
    private ImageView back;


    @Override
    protected int getLayout() {
        return R.layout.im_info;
    }

    @Override
    protected void initViews() {
        back = bindView(R.id.iv_finish_chat);
        groupInformation = bindView(R.id.information_chat);
        rvChat = bindView(R.id.rv_chat);
        sendChat = bindView(R.id.send_chat);
        etChat = bindView(R.id.et_chat);

        groupCreateRL = bindView(R.id.group_create);
        chatMainLL = bindView(R.id.chat_main);
        otherRlContainer = bindView(R.id.other_rl_container);
        otherFabCircle = bindView(R.id.other_fab_circle);
        createGroupBtn = (Button) findViewById(R.id.btn_create_group);
        setClick(this, createGroupBtn, groupInformation, sendChat, back);
    }

    @Override
    protected void initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        } else {
            otherFabCircle.setVisibility(View.INVISIBLE);
//            chatMainLL.setVisibility(View.VISIBLE);


            if (Values.GROUP_ID.equals("")) { // 如果没有群组
                groupCreateRL.setVisibility(View.VISIBLE);
                chatMainLL.setVisibility(View.INVISIBLE);
            } else { // 如果有群组
                chatMainLL.setVisibility(View.VISIBLE);
                groupCreateRL.setVisibility(View.INVISIBLE);
                // 注册当前类可以监听消息
                EMClient.getInstance().chatManager().addMessageListener(ChatInfoActivity.this);
            }
        }


        arrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter();
        rvChat.setAdapter(chatRVAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(linearLayoutManager);
        // 获取当前对话的对话记录
        initChatData();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (Values.GROUP_ID.equals("")) { // 如果没有群组
            groupCreateRL.setVisibility(View.VISIBLE);
            chatMainLL.setVisibility(View.INVISIBLE);
        } else { // 如果有群组
            chatMainLL.setVisibility(View.VISIBLE);
            groupCreateRL.setVisibility(View.INVISIBLE);
            // 注册当前类可以监听消息
            EMClient.getInstance().chatManager().addMessageListener(ChatInfoActivity.this);
            // 群组成员信息
            groupInfo();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_group:
                Intent intent = new Intent(ChatInfoActivity.this, CreateGroupActivity.class);
                startActivity(intent);
                break;

            case R.id.send_chat:
                sendMessage();
                break;

            case R.id.information_chat:
                //根据群组ID从服务器获取群组基本信息
                showChart();
                break;
            case R.id.iv_finish_chat:
                finish();
                break;


        }
    }

    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(0);

    }

    //显示历史评分
    private void showChart() {
//        new GroupInformationFragment().show(getFragmentManager(),"dialog");
        builder = new AlertDialog.Builder(this);
        builder.setTitle("群组信息").setIcon(R.mipmap.ic_launcher);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chat_view, null);
        builder.setView(view);
        dialogLV = (ListView) view.findViewById(R.id.lv_dialog_chat);
        inventBtn = (Button) view.findViewById(R.id.btn_chat_invent);
        inventEt = (EditText) view.findViewById(R.id.et_chat_invent);
        chatLVAdapter = new ChatLVAdapter();
        chatLVAdapter.setOwner(owner);
        chatLVAdapter.setArrayList(membersList);
        dialogLV.setAdapter(chatLVAdapter);


        inventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] ID = {inventEt.getText().toString().trim()};
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //私有群里，如果开放了群成员邀请，群成员邀请调用下面方法
                        try {
                            EMClient.getInstance().groupManager().inviteUser(Values.GROUP_ID, ID, null);//需异步处理
                            ToastUtil.showShortToast("成员添加成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ToastUtil.showShortToast("成员添加失败");
                        }
                    }
                }).start();
            }
        });


        builder.show();

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


    }


    private void groupInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                group = null;
                Log.d("群组群主信息", "Values.OWNER:" + Values.OWNER);
                try {
                    group = EMClient.getInstance().groupManager().getGroupFromServer(Values.GROUP_ID);
                    membersList = group.getMembers();
                    owner = group.getOwner();
                    Log.d("群组成员信息", "group.getMembers():" + group.getMembers());
                    Log.d("群组群主信息", group.getOwner());

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(
                this, otherRlContainer,
                otherFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initTheViews();
                    }
                });


    }

    private void initTheViews() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(300);
        chatMainLL.startAnimation(animation);
        chatMainLL.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GuiUtils.animateRevealHide(
                    this, otherRlContainer,
                    otherFabCircle.getWidth() / 2, R.color.colorAccent,
                    new GuiUtils.OnRevealAnimationListener() {
                        @Override
                        public void onRevealHide() {
                            defaultBackPressed();
                        }

                        @Override
                        public void onRevealShow() {

                        }
                    });
        } else {
            defaultBackPressed();
        }
    }

    private void defaultBackPressed() {
        super.onBackPressed();

    }

    private void sendMessage() {

        String content = etChat.getText().toString();
        Log.d("123123123123123123", content + "123123");
        if (!content.equals("")) {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，
            EMMessage message = EMMessage.createTxtSendMessage(content, Values.GROUP_ID);
            //如果是群聊，设置chattype，
            message.setChatType(EMMessage.ChatType.GroupChat);
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);


            ChatBean chatBean = new ChatBean();
            chatBean.setMsg(etChat.getText().toString());
            chatBean.setType(CHAT_SELF);
            chatBean.setIconUrl(Values.ICON_URL);
            chatBean.setNickname(Values.NICKNAME);
            arrayList.add(chatBean);
            chatRVAdapter.setArrayList(arrayList);
            rvChat.smoothScrollToPosition(arrayList.size());
            etChat.setText("");
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {

                    Log.d("ChatActivity", "消息发送成功");
                }

                @Override
                public void onError(int i, String s) {
                    Log.d("ChatActivity", "消息发送失败" + "  " + i + "  " + s);
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }


    // 接收聊天消息
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage message : list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("ChatInfoActivity", message.getUserName());
                    // 通过接收到的信息查询Bmob
                    query(message.getUserName(), ((EMTextMessageBody) message.getBody()).getMessage());

                }
            });

        }
    }

    private void query(String username, final String msg) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("username", username);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
//执行查询方法
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
                    for (MyUser gameScore : object) {
                        Log.d("接收过来的iconUrl", gameScore.getIcon());
                        Log.d("接收过来的nickname", gameScore.getNickname());
                        ChatBean chatBean = new ChatBean();
                        chatBean.setMsg(msg);
                        chatBean.setType(CHAT_OTHERS);
                        chatBean.setIconUrl(gameScore.getIcon());
                        chatBean.setNickname(gameScore.getNickname());
                        arrayList.add(chatBean);
                        chatRVAdapter.setArrayList(arrayList);
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    private void initChatData(){
//        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(Values.USER_NAME, EMConversation.EMConversationType.GroupChat);
////获取此会话的所有消息
//        List<EMMessage> messages = conversation.getAllMessages();
//
//        Log.d("ChatInfoActivity", "messages.get(0).getBody():" + messages.get(0).getBody());
////SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
////获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
////        List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
    }

}