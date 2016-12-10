package com.example.dllo.dorm.mainpage.chat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.toast.ToastUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by SongQingJun on 16/12/6.
 */
public class CreateGroupActivity extends BaseActivity {

    private Button createBtn;
    private EditText groupName;
    private EditText groupInformation;

    @Override
    protected int getLayout() {
        return R.layout.creat_group;
    }

    @Override
    protected void initViews() {
        createBtn = bindView(R.id.group_create);
        groupName = bindView(R.id.group_name_create);
        groupInformation = bindView(R.id.group_information_create);
    }

    @Override
    protected void initData() {
        create();
        // 群组邀请监听
    }




    private void create() {
        // 创建群组
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String information = groupInformation.getText().toString();
                String name = groupName.getText().toString();

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
                option.maxUsers = 200;
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                String people[] = {};
                try {
                    EMGroup emGroup = EMClient.getInstance().groupManager().createGroup(information, name, people, "createGroup", option);
                    Log.d("ECMainActivity", emGroup.getGroupId());
                    Values.GROUP_ID = emGroup.getGroupId();
                    ToastUtil.showShortToast("群组创建成功");
                    finish();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
