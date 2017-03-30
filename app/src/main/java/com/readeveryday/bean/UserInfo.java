package com.readeveryday.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by XuYanping on 2017/3/29.
 */

public class UserInfo extends BmobObject{

    private String userName;
    private String psw;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
