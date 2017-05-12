package com.readeveryday.api;

import android.text.TextUtils;
import android.util.Log;

import com.readeveryday.BuildConfig;
import com.readeveryday.MyApplication;
import com.readeveryday.utils.StateUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class ApiRetrofit {

    public ZhiHuApi ZhuHuApiServer;
    public GankApi GankApiServer;
    public DailyApi DailyApiServer;
    public AndroidApi AndroidApiServer;

    public static final String ZHIHU_BASE_URL = "http://news-at.zhihu.com/api/4/";
    public static final String GANK_BASE_URL = "http://gank.io/api/";
    public static final String ANDROID_BASE_URL = "http://gank.io/api/data/Android/10/";
    public static final String DAILY_BASE_URL = "http://app3.qdaily.com/app3/";

    public ZhiHuApi getZhuHuApiServer() {
        return ZhuHuApiServer;
    }

    public GankApi getGankApiServer() {
        return GankApiServer;
    }

    public DailyApi getDailyApiServer() {
        return DailyApiServer;
    }

    public AndroidApi getAndroidApiServer() {
        return AndroidApiServer;
    }

    public ApiRetrofit() {

        File httpCacheDirectory = new File(MyApplication.mContext.getCacheDir(), "dataResponse");
        int cacheSize = 1024 * 1024 * 10;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).cache(cache).connectTimeout(3000, TimeUnit.SECONDS)//设置超时时间
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (TextUtils.isEmpty(message)) return;
                    String s = message.substring(0, 1);
                    //如果收到想响应是json才打印
                    if ("{".equals(s) || "[".equals(s)) {
                        Log.d("postRequest", "收到响应：" + message);
                    }
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }


        Retrofit retrofit_zhihu = new Retrofit.Builder().baseUrl(ZHIHU_BASE_URL).client(client.build()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        Retrofit retrofit_gank = new Retrofit.Builder().baseUrl(GANK_BASE_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        Retrofit retrofit_daily = new Retrofit.Builder().baseUrl(DAILY_BASE_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        Retrofit retrofit_android = new Retrofit.Builder().baseUrl(ANDROID_BASE_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        ZhuHuApiServer = retrofit_zhihu.create(ZhiHuApi.class);
        GankApiServer = retrofit_gank.create(GankApi.class);
        DailyApiServer = retrofit_daily.create(DailyApi.class);
        AndroidApiServer = retrofit_android.create(AndroidApi.class);

    }

    public class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder builder = new CacheControl.Builder();
            //这个是控制缓存的最大生命时间   maxAge设置为0，不会取缓存，直接走网络。
            //此处设置缓存时间为一小时
            builder.maxAge(1, TimeUnit.HOURS);
            //这个是控制缓存的过时时间
            builder.maxStale(365, TimeUnit.DAYS);

            CacheControl cacheControl = builder.build();
            Request request = chain.request();

            //打印发送请求
            if (BuildConfig.DEBUG) {
                Log.d("postRequest", "发送请求：" + request.url() + "-" + chain.connection() + "-" + request.headers());
            }


            if (!StateUtil.isNetworkAvailable(MyApplication.mContext)) {
                request = request.newBuilder().cacheControl(cacheControl).build();
            }
            Response originalResponse = chain.proceed(request);
            if (StateUtil.isNetworkAvailable(MyApplication.mContext)) {
                int maxAge = 0;
                return originalResponse.newBuilder().removeHeader("Pragma").header("Cache-Control", "public ,max-age=" + maxAge).build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder().removeHeader("Pragma").header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
            }
        }
    }

//    //拦截器
//    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//
//        }
//    };


}
