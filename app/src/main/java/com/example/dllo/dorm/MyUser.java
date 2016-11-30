package com.example.dllo.dorm;

import cn.bmob.v3.BmobUser;

/**
 * Created by zhaojun on 16/11/29.
 */
public class MyUser extends BmobUser{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
