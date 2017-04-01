package com.readeveryday.manager;

import android.content.Context;
import android.content.SharedPreferences;

import static com.readeveryday.MyApplication.mContext;

/**
 * Created by XuYanping on 2017/3/31.
 */

public class SharedPreferencesManager {

    private static SharedPreferencesManager manager;

    public SharedPreferences mPreferencesl;

    public SharedPreferencesManager() {
        mPreferencesl = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getSharedPreferencesManager() {
        if (manager == null) {
            synchronized (SharedPreferencesManager.class) {
                if (manager == null) {
                    manager = new SharedPreferencesManager();
                }
            }
        }
        return manager;
    }

    private void initShared() {
        SharedPreferences.Editor editor = mPreferencesl.edit();
        editor.putString("userName", "");
        editor.putString("psw", "");
        editor.putBoolean("isLoging", false);
        editor.apply();
    }
}
