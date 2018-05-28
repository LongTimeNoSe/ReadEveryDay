package com.readeveryday.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.readeveryday.bean.gank.Gank;
import com.readeveryday.bean.gank.Meizhi;
import com.readeveryday.bean.gank.Video;
import com.readeveryday.ui.adapter.GankAdapter;
import com.readeveryday.base.BasePresenter;
import com.readeveryday.ui.view.GankFgView;
import com.readeveryday.utils.DateUtils;
import com.readeveryday.utils.PromptUtil;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class GankPresenter extends BasePresenter<GankFgView> {

    private static final String TAG = "GankPresenter";

    private Context mContext;
    private GankFgView mGankFgView;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private List<Gank> mList;
    private GankAdapter mAdapter;
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
            Observable.zip(gankApi.getMeizhiData(page), gankApi.getVideoData(page), new Func2<Meizhi, Video, Meizhi>() {
                @Override
                public Meizhi call(Meizhi meizhi, Video video) {
                    return creatDesc(meizhi, video);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Meizhi>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, e.toString());
                    PromptUtil.toastShowShort(mContext, "网络不见了~");
                }

                @Override
                public void onNext(Meizhi meizhi) {
                    displayMeizhi(mContext, meizhi.getResults(), mGankFgView, mRecyclerView);
                }
            });
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

    private void displayMeizhi(Context context, List<Gank> meiZhiList, GankFgView gankFgView, RecyclerView recyclerView) {

        if (isLoadMore) {
            if (meiZhiList == null) {
                gankFgView.setDataRefresh(false);
            } else {
                mList.addAll(meiZhiList);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            mList = meiZhiList;
            mAdapter = new GankAdapter(context, mList);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        gankFgView.setDataRefresh(false);
    }

    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                }
                if (mLayoutManager.getItemCount() == 1) return;

                if (lastVisibleItem + 1 == mLayoutManager.getItemCount()) {
                    mGankFgView.setDataRefresh(true);
                    isLoadMore = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getGankData();
                        }
                    }, 1000);
                }
            }
        });
    }
}
