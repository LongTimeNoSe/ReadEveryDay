package com.readeveryday.api;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class ApiFactory {

    protected static final Object monitor = new Object();
    static ZhiHuApi ZhiHuSingleton = null;
    static GankApi GankSingleton = null;
    static DailyApi DailySingleton = null;

    public static ZhiHuApi getZhiHuSingleton() {

        synchronized (monitor) {
            if (ZhiHuSingleton == null) {
                ZhiHuSingleton = new ApiRetrofit().getZhuHuApiServer();
            }
            return ZhiHuSingleton;
        }
    }

    public static GankApi getGankSingleton() {
        synchronized (monitor) {
            if (GankSingleton == null) {
                GankSingleton = new ApiRetrofit().getGankApiServer();
            }
            return GankSingleton;
        }
    }

    public static DailyApi getDailySingleton() {
        synchronized (monitor) {
            if (DailySingleton == null) {
                DailySingleton = new ApiRetrofit().getDailyApiServer();
            }
            return DailySingleton;
        }
    }
}
