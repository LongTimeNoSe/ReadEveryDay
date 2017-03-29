package com.readeveryday;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readeveryday.greendao.DaoMaster;
import com.readeveryday.greendao.DaoSession;
import com.readeveryday.manager.GreenDaoManager;
import com.umeng.analytics.MobclickAgent;

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
        //ShareSDK分享
        ShareSDK.initSDK(mContext);
        //友盟统计
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
        // EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //数据库
        GreenDaoManager.getGreenDaoManager(mContext);
    }

}
