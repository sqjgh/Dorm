package com.example.dllo.dorm.firstpage.chat;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wanghuan on 16/11/24.
 */
public class ChatInfoActivity extends BaseActivity implements EMMessageListener,View.OnClickListener {
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
    @Override
    protected int getLayout() {
        return R.layout.im_info;
    }

    @Override
    protected void initViews() {
        groupInformation = bindView(R.id.information_chat);
        rvChat = bindView(R.id.rv_chat);
        sendChat = bindView(R.id.send_chat);
        etChat = bindView(R.id.et_chat);

        groupCreateRL = bindView(R.id.group_create);
        chatMainLL = bindView(R.id.chat_main);
        otherRlContainer = bindView(R.id.other_rl_container);
        otherFabCircle = bindView(R.id.other_fab_circle);
        createGroupBtn = (Button) findViewById(R.id.btn_create_group);
        setClick(this,createGroupBtn,groupInformation,sendChat);
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        }else {
            otherFabCircle.setVisibility(View.INVISIBLE);
//            chatMainLL.setVisibility(View.VISIBLE);
        }


        arrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter();
        rvChat.setAdapter(chatRVAdapter);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvChat.setLayoutManager(linearLayoutManager);

    }



    @Override
    protected void onResume() {
        super.onResume();

        if (Values.GROUP_ID.equals("")){ // 如果没有群组
            groupCreateRL.setVisibility(View.VISIBLE);
            chatMainLL.setVisibility(View.INVISIBLE);
        }else { // 如果有群组
            chatMainLL.setVisibility(View.VISIBLE);
            groupCreateRL.setVisibility(View.INVISIBLE);
            // 注册当前类可以监听消息
            EMClient.getInstance().chatManager().addMessageListener(ChatInfoActivity.this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_group:
                Intent intent = new Intent(ChatInfoActivity.this, CreateGroupActivity.class);
                startActivity(intent);
                break;

            case R.id.send_chat:
                sendMessage();
                break;

        }
    }

    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(0);

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
        }else {
            defaultBackPressed();
        }
    }

    private void defaultBackPressed() {
        super.onBackPressed();

    }
    private void sendMessage(){

        String content = etChat.getText().toString();
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，
        EMMessage message = EMMessage.createTxtSendMessage(content, Values.GROUP_ID);
        //如果是群聊，设置chattype，
        message.setChatType(EMMessage.ChatType.GroupChat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);


        ChatBean chatBean = new ChatBean();
        chatBean.setMsg(etChat.getText().toString());
        chatBean.setType(CHAT_SELF);
        arrayList.add(chatBean);
        chatRVAdapter.setArrayList(arrayList);


        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("ChatActivity", "消息发送成功");
//TODO     问问           ToastUtil.showShortToast("消息发送成功");
                Log.d("SqjTestChat", "zzzzzzzzzzzzzzzzzzzzzzzz");
            }

            @Override
            public void onError(int i, String s) {
                Log.d("ChatActivity", "消息发送失败" + "  " + i + "  " + s);
//TODO     问问          ToastUtil.showShortToast("消息发送失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }



    // 接收聊天消息
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage message : list) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Log.d("C123", "message.getBody():" + ((EMTextMessageBody)message.getBody()).getMessage());
                    ChatBean chatBean = new ChatBean();
                    chatBean.setMsg(((EMTextMessageBody)message.getBody()).getMessage());
                    chatBean.setType(CHAT_OTHERS);
                    arrayList.add(chatBean);
                    chatRVAdapter.setArrayList(arrayList);
                }
            });

        }
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


}
