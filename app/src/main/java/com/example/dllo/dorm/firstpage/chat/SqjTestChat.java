package com.example.dllo.dorm.firstpage.chat;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongQingJun on 16/11/29.
 */

public class SqjTestChat extends BaseActivity implements View.OnClickListener,EMMessageListener {

    private RecyclerView rvChat;
    private Button sendChat;
    private EditText etChat;
    private ImageView information;
    private Intent intent;
    private String groupID;
    private ArrayList<ChatBean> arrayList;
    private ChatRVAdapter chatRVAdapter;
    private final int CHAT_SELF = 0;  // 自己发送的消息类型
    private final int CHAT_OTHERS = 1;  // 接收到的消息类型
    @Override
    protected int getLayout() {
        return R.layout.chat_test;
    }

    @Override
    protected void initViews() {
        information = bindView(R.id.information_chat);
        rvChat = bindView(R.id.rv_chat);
        sendChat = bindView(R.id.send_chat);
        etChat = bindView(R.id.et_chat);

        setClick(this,information,sendChat);
    }

    @Override
    protected void initData() {
        intent = getIntent();
        groupID = intent.getStringExtra("groupID");

        arrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter();
        rvChat.setAdapter(chatRVAdapter);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvChat.setLayoutManager(linearLayoutManager);

        Log.d("SqjTestChat", intent.getStringExtra("groupID"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.information_chat:
                //根据群组ID从服务器获取群组基本信息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(intent.getStringExtra("groupID"));
                            //获取群成员
                            for (int i = 0; i < group.getMembers().size(); i++) {
                                Log.d("SqjTestChat", "群成员" + group.getMembers().get(i));
                            }

                            //获取群主
                            Log.d("SqjTestChat", "群主:" + group.getOwner());

                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.send_chat:
                ChatBean chatBean = new ChatBean();
                chatBean.setMsg(etChat.getText().toString());
                chatBean.setType(CHAT_SELF);
                arrayList.add(chatBean);
                chatRVAdapter.setArrayList(arrayList);
                sendMessage();
                break;

        }
    }

    private void sendMessage(){
        String content = etChat.getText().toString();
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, groupID);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.GroupChat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);



        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("ChatActivity", "消息发送成功");
                ToastUtil.showShortToast("消息发送成功");


                Log.d("SqjTestChat", "zzzzzzzzzzzzzzzzzzzzzzzz");
            }

            @Override
            public void onError(int i, String s) {
                Log.d("ChatActivity", "消息发送失败" + "  " + i + "  " + s);
                ToastUtil.showShortToast("消息发送失败");
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

                    ChatBean chatBean = new ChatBean();
                    chatBean.setMsg(((EMTextMessageBody)message.getBody()).getMessage());
                    chatBean.setType(CHAT_OTHERS);
                    arrayList.add(chatBean);
                    chatRVAdapter.setArrayList(arrayList);


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

    // 注册当前类可以监听消息
    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


}
