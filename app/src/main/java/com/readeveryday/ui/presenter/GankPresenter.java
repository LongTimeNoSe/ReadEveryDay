package com.readeveryday.ui.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.readeveryday.bean.gank.Gank;
import com.readeveryday.bean.gank.Meizhi;
import com.readeveryday.bean.gank.Video;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.GankFgView;
import com.readeveryday.utils.DateUtils;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class GankPresenter extends BasePresenter<GankFgView> {

    private Context mContext;
    private GankFgView mGankFgView;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private List<Gank> mList;
    private int page = 1;
    private int lastVisibleItem;
    private boolean isLoadMore;

    public GankPresenter(Context context) {
        mContext = context;
    }

    public void getGankData() {

        mGankFgView = getView();
        if (mGankFgView != null) {
            mRecyclerView = mGankFgView.getRecyclerView();
            mLayoutManager = mGankFgView.getLayoutManager();
            if (isLoadMore) {
                page += 1;
            }

        }
    }


    /**
     * MeiZhi = list , gankmeizhi = 福利
     *
     * @param meizhi list
     * @param video  list
     * @return
     */
    private Meizhi creatDesc(Meizhi meizhi, Video video) {
        for (Gank gankmeizhi : meizhi.getResults()) {
            gankmeizhi.desc = gankmeizhi.desc + " " +
                    getVideoDesc(gankmeizhi.getPublishedAt(), video.getResults());
        }
        return meizhi;
    }

    //匹配同一天的福利描述和视频描述
    private String getVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = 0; i < results.size(); i++) {
            Gank video = results.get(i);
            if (video.getPublishedAt() == null) video.setPublishedAt(video.getCreatedAt());
            if (DateUtils.isSameDate(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc();
                break;
            }
        }
        return videoDesc;
    }
}
