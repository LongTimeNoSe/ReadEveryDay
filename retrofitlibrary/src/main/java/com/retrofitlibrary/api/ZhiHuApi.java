package com.retrofitlibrary.api.util;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by XuYanping on 2017/3/15.
 */

public interface ZhiHuApi {

    @GET("start-image/1080*1920")
    Observable<Object> getSplashImage();

    @GET("news/latest")
    Observable<Object> getLatestNews();

    @GET("news/before/{time}")
    Observable<Object> getBeforetNews(@Path("time") String time);

    @GET("news/{id}")
    Observable<Object> getDetailNews(@Path("id") String id);

}
