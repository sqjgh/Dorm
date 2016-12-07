package com.example.dllo.dorm;

import cn.bmob.v3.BmobUser;

/**
 * Created by zhaojun on 16/11/29.
 */
public class MyUser extends BmobUser{
    String nickname;
    String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
