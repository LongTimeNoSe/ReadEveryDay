package com.readeveryday.manager;

import android.content.Context;

import com.readeveryday.greendao.UserInfo;
import com.readeveryday.greendao.UserInfoDao;

import java.util.List;

/**
 * Created by XuYanping on 2017/3/31.
 */

public class GreenDaoUserInfoManager {

    private Context mContext;
    private UserInfo mUserInfo;
    private UserInfoDao mUserInfoDao;

    public GreenDaoUserInfoManager(Context context) {
        mContext = context;
    }


    //数据库增加
    public void insertUserInfo(String name, String psw, boolean isLogin) {
        mUserInfo = new UserInfo(name, psw, isLogin);
        mUserInfoDao.insert(mUserInfo);
    }

    //数据库查询
    public boolean queryIsLogin(String name) {
        List<UserInfo> list = mUserInfoDao.queryBuilder().where(UserInfoDao.Properties.UserName.eq(name)).build().list();
        return list.get(0).getIsLoging();
    }

    //数据库删除
    public void deleteUserInfo(String name) {
        List<UserInfo> list = mUserInfoDao.queryBuilder().where(UserInfoDao.Properties.UserName.eq(name)).build().list();
        for (UserInfo item : list) {
            mUserInfoDao.delete(item);
        }
    }

    //数据库更改
    public void updateUserInfo(String name, String psw) {
        List<UserInfo> list = mUserInfoDao.queryBuilder().where(UserInfoDao.Properties.UserName.eq(name)).build().list();
        for (UserInfo item : list) {
            item.setUserName(name);
            item.setPassWord(psw);
            mUserInfoDao.update(item);
        }
    }

}
