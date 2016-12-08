package com.example.dllo.dorm.firstpage.chat;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dllo.dorm.R;

/**
 * Created by SongQingJun on 16/12/8.
 */

public class GroupInformationFragment extends DialogFragment{

    private ListView dialogLV;
    private Button inventBtn;
    private EditText inventEt;
    private ChatLVAdapter chatLVAdapter;
    private View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_chat_view, null);
        System.out.println("tag = "+getTag()); // tag which is from acitivity which started this fragment

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogLV = (ListView) v.findViewById(R.id.lv_dialog_chat);
        inventBtn = (Button) v.findViewById(R.id.btn_chat_invent);
        inventEt = (EditText) v.findViewById(R.id.et_chat_invent);

        chatLVAdapter = new ChatLVAdapter();
//        inventBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String []ID = {inventEt.getText().toString().trim()};
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //私有群里，如果开放了群成员邀请，群成员邀请调用下面方法
//                        try {
//                            EMClient.getInstance().groupManager().inviteUser(Values.GROUP_ID, ID, null);//需异步处理
//                        } catch (HyphenateException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//        });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EMGroup group = null;
//                try {
//                    group = EMClient.getInstance().groupManager().getGroupFromServer(Values.GROUP_ID);
//                    Log.d("群组成员信息", "group.getMembers():" + group.getMembers());
//                    Log.d("群组群主信息", group.getOwner());
//
//                    chatLVAdapter.setArrayList(group.getMembers());//获取群成员
//                    chatLVAdapter.setOwner(group.getOwner()); //获取群主
//                    dialogLV.setAdapter(chatLVAdapter);
//
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }


}
