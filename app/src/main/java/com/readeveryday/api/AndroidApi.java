package com.readeveryday.api;

import com.readeveryday.bean.Android.AndroidList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by XuYanping on 2017/3/17.
 */

public interface AndroidApi {

    @GET("{page}")
    Observable<AndroidList> getAndroidList(@Path("page") int page);
}
