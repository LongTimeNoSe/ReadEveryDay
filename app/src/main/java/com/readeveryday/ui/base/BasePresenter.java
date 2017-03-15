package com.readeveryday.ui.base;

import com.readeveryday.api.ApiFactory;
import com.readeveryday.api.DailyApi;
import com.readeveryday.api.GankApi;
import com.readeveryday.api.ZhiHuApi;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by XuYanping on 2017/3/15.
 */

public abstract class BasePresenter<V> {

    protected Reference<V> mReference;

    public static final ZhiHuApi zhihuApi = ApiFactory.getZhiHuSingleton();
    public static final GankApi gankApi = ApiFactory.getGankSingleton();
    public static final DailyApi dailyApi = ApiFactory.getDailySingleton();

    //附加View (弱引用)
    public void attachView(V view) {
        mReference = new WeakReference<V>(view);
    }

    protected V getView() {
        return mReference.get();
    }

    public boolean isViewAttached() {
        return (mReference != null) && mReference.get() != null;
    }

    //分离View
    public void detachView() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }
}
