package com.readeveryday.api;

import com.readeveryday.bean.gank.GankData;
import com.readeveryday.bean.gank.Meizhi;
import com.readeveryday.bean.gank.Video;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by XuYanping on 2017/3/15.
 */

public interface GankApi {

    @GET("data/福利/10/{page}")
    Observable<Meizhi> getMeizhiData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("data/休息视频/10/{page}")
    Observable<Video> getVideoData(@Path("page") int page);
}
