package com.readeveryday.api;

import com.readeveryday.bean.zhihu.News;
import com.readeveryday.bean.zhihu.NewsTimeLine;
import com.readeveryday.bean.zhihu.SplashImage;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by XuYanping on 2017/3/15.
 */

public interface ZhiHuApi {

    @GET("start-image/1080*1920")
    Observable<SplashImage> getSplashImage();

    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews();

    @GET("news/before/{time}")
    Observable<NewsTimeLine> getBeforetNews(@Path("time") String time);

    @GET("news/{id}")
    Observable<News> getDetailNews(@Path("id") String id);

}
