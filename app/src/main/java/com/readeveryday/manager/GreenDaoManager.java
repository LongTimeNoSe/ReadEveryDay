package com.readeveryday.manager;

import android.content.Context;

import com.readeveryday.greendao.DaoMaster;
import com.readeveryday.greendao.DaoSession;

/**
 * Created by XuYanping on 2017/3/23.
 */

public class GreenDaoManager {

  private Context mContext;
  private DaoMaster.DevOpenHelper mHelper;
  private DaoMaster mDaoMaster;
  private DaoSession mDaoSession;
  private static GreenDaoManager greenDaoManager;

  public GreenDaoManager(Context context) {
    mContext = context;
    mHelper = new DaoMaster.DevOpenHelper(mContext, "read_everyday_db", null);
    mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
    mDaoSession = mDaoMaster.newSession();
  }

  public static GreenDaoManager getGreenDaoManager(Context context) {
    if (greenDaoManager == null) {
      synchronized (GreenDaoManager.class) {
        if (greenDaoManager == null) {
          greenDaoManager = new GreenDaoManager(context);
        }
      }
    }

    return greenDaoManager;
  }

  public DaoMaster.DevOpenHelper getHelper() {
    return mHelper;
  }

  public DaoMaster getDaoMaster() {
    return mDaoMaster;
  }

  public DaoSession getDaoSession() {
    return mDaoSession;
  }

  public void inntDatabase() {

  }

  public void closeDB() {
    if (mHelper != null) {
      mHelper.close();
    }
  }
}