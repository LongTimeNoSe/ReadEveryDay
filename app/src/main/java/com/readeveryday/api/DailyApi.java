package com.readeveryday.api;

import com.readeveryday.bean.daily.DailyTimeLine;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by XuYanping on 2017/3/15.
 */

public interface DailyApi {

    @GET("homes/index/{num}.json")
    Observable<DailyTimeLine> getDailyTimeLine(@Path("num") String num);

    @GET("options/index/{id}/{num}.json")
    Observable<DailyTimeLine> getDailyFeedDetail(@Path("id") String id, @Path("num") String num);

}
