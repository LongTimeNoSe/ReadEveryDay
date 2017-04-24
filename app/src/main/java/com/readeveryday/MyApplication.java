package com.readeveryday;

import android.app.Application;
import android.content.Context;

import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.manager.SharedPreferencesManager;
import com.umeng.analytics.MobclickAgent;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
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
        boolean isRelase = true;
        if (isRelase) {
            MobclickAgent.setDebugMode(true);
            // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
            // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
            MobclickAgent.openActivityDurationTrack(false);
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        }
        //数据库
        GreenDaoManager.getGreenDaoManager(mContext);
        /**
         * Bmob后端云（实现登录注册功能）
         */
        //Bmob.initialize(mContext, Constants.BMOBKEY);
        BmobConfig config = new BmobConfig.Builder(mContext)
                ////设置appkey
                .setApplicationId(Constants.BMOBKEY)
                ////请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                ////文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                ////文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500).build();
        Bmob.initialize(config);
        //初始化shared
        SharedPreferencesManager.getSharedPreferencesManager();
    }
}
