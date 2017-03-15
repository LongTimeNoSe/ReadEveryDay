package com.readeveryday;

import android.app.Application;
import android.content.Context;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
