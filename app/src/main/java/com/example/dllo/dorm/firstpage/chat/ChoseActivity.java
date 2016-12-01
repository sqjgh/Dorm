package com.example.dllo.dorm.firstpage.chat;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by SongQingJun on 16/11/29.
 */

public class ChoseActivity extends BaseActivity implements View.OnClickListener {

    private EditText join;
    private Button joinBtn;
    private Button createGroupBtn;

    @Override
    protected int getLayout() {
        return R.layout.chose_activity;
    }

    @Override
    protected void initViews() {
        join = (EditText) findViewById(R.id.et_group_number);
        joinBtn = (Button) findViewById(R.id.btn_group_number);
        createGroupBtn = (Button) findViewById(R.id.btn_create_group);
        setClick(this,joinBtn,createGroupBtn);
    }

    @Override
    protected void initData() {
        // 获取群组成员与群主
//        groupPeople();
        // 自己加入的群组信息
//        groupList();

        // 获取网络群组列表
        EMClient.getInstance().groupManager().loadAllGroups();
    }

    private void groupList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    List<EMGroup> groupList = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
//                    for (int i = 0; i < groupList.size(); i++) {
//                        Log.d("ChoseActivity", groupList.get(i).getGroupId());
//                    }
                    // 加入群组聊天
//                    String inputGroupID = join.getText().toString();
                    Log.d("ChoseActivity", groupList.get(0).getGroupId());
                    String inputGroupID = groupList.get(0).getGroupId();
                    Intent intent1 = new Intent(ChoseActivity.this, SqjTestChat.class);
                    intent1.putExtra("groupID", inputGroupID);
                    startActivity(intent1);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




    // 点击监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_group_number:
                // 加入群组聊天
                String inputGroupID = join.getText().toString();
                Intent intent1 = new Intent(ChoseActivity.this, SqjTestChat.class);
                intent1.putExtra("groupID", inputGroupID);
                startActivity(intent1);

                break;
            case R.id.btn_create_group:
                // 创建群组
                createGroupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 创建群组
                         * @param groupName 群组名称
                         * @param desc 群组简介
                         * @param allMembers 群组初始成员，如果只有自己传空数组即可
                         * @param reason 邀请成员加入的reason
                         * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
                         * @return 创建好的group
                         * @throws HyphenateException
                         */
                        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
                        option.maxUsers = 8;
                        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                        String people[] = {};
                        try {
                            EMGroup emGroup = EMClient.getInstance().groupManager().createGroup("test01", "你好test01", people, "forTest", option);
                            Log.d("ECMainActivity", emGroup.getGroupId());
                            ToastUtil.showShortToast("群组创建成功");
                            Intent intent = new Intent(ChoseActivity.this, SqjTestChat.class);
                            intent.putExtra("groupID", emGroup.getGroupId());
                            startActivity(intent);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;

        }
    }
}
