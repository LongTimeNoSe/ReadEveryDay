package com.readeveryday.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by XuYanping on 2017/3/31.
 */

public class SharedPreferencesManager {
    private Context mContext;

    private static SharedPreferencesManager manager;

    private SharedPreferences mPreferencesl;

    public SharedPreferencesManager(Context context) {
        mContext = context;
        mPreferencesl = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getSharedPreferencesManager(Context context) {
        if (manager == null) {
            synchronized (SharedPreferencesManager.class) {
                if (manager == null) {
                    manager = new SharedPreferencesManager(context);
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
