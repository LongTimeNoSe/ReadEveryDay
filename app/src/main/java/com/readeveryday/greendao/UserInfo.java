package com.readeveryday.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by XuYanping on 2017/3/31.
 */

@Entity
public class UserInfo {

    @Id
    private Long Id;
    private String userName;
    private String passWord;
    private boolean isLoging;

    public UserInfo(String userName, String passWord, boolean isLoging) {
        this.userName = userName;
        this.passWord = passWord;
        this.isLoging = isLoging;
    }

    @Generated(hash = 530126841)
    public UserInfo(Long Id, String userName, String passWord, boolean isLoging) {
        this.Id = Id;
        this.userName = userName;
        this.passWord = passWord;
        this.isLoging = isLoging;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean getIsLoging() {
        return this.isLoging;
    }

    public void setIsLoging(boolean isLoging) {
        this.isLoging = isLoging;
    }
}
