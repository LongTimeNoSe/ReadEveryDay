package com.readeveryday;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readeveryday.greendao.DaoMaster;
import com.readeveryday.greendao.DaoSession;
import com.readeveryday.manager.GreenDaoManager;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        ShareSDK.initSDK(mContext);
        GreenDaoManager.getGreenDaoManager(mContext);
    }

}
