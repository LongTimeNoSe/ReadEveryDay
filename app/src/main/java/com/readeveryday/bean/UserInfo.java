package com.readeveryday.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by XuYanping on 2017/3/29.
 */

public class UserInfo extends BmobUser{

    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
