package com.readeveryday.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.readeveryday.bean.Android.AndroidList;
import com.readeveryday.ui.adapter.AndroidListAdapter;
import com.readeveryday.base.BasePresenter;
import com.readeveryday.ui.view.AndroidView;
import com.readeveryday.utils.PromptUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XuYanping on 2017/3/16.
 */

public class AndroidPresenter extends BasePresenter<AndroidView> {

    private Context mContext;
    private AndroidView mAndroidView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AndroidList mAndroidList;
    private int lastVisibleItem;
    private AndroidListAdapter mAdapter;
    private int page = 1;
    private boolean isLoadMore = false; //是否加载更多

    public AndroidPresenter(Context context) {
        mContext = context;
    }

    public void getDatas() {

        mAndroidView = getView();
        if (mAndroidView != null) {
            mRecyclerView = mAndroidView.getRecyclerView();
            mLayoutManager = mAndroidView.getLayoutManager();

            if (isLoadMore) {
                page += 1;
            }
            androidApi.getAndroidList(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AndroidList>() {
                @Override
                public void onCompleted() {
                    mAndroidView.setDataRefresh(false);
                }

                @Override
                public void onError(Throwable e) {
                    if (e.toString().contains("404")) {
                        PromptUtil.toastShowShort(mContext, "讶，404啦");
                    }else{
                        PromptUtil.toastShowShort(mContext, e.toString());
                    }
                    mAndroidView.setDataRefresh(false);
                }

                @Override
                public void onNext(AndroidList list) {

                    setDatas(mContext, list, mAndroidView, mRecyclerView);
                }
            });
        }
    }


    private void setDatas(Context context, AndroidList list, AndroidView view, RecyclerView recycleView) {
        if (isLoadMore) {
            mAndroidView.setDataRefresh(false);
            if (list.getResults() == null) {
                mAdapter.updateLoadStatus(mAdapter.LOAD_NONE);
            } else {
                mAndroidList.getResults().addAll(list.getResults());
            }
            mAdapter.notifyDataSetChanged();
        } else {
            mAndroidList = list;
            mAdapter = new AndroidListAdapter(context, mAndroidList.getResults());
            recycleView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        view.setDataRefresh(false);
    }

    public void onScrollRecycleView() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        mAdapter.updateLoadStatus(AndroidListAdapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == mLayoutManager.getItemCount()) {
                        mAdapter.updateLoadStatus(mAdapter.LOAD_PULL_TO);
                        mAndroidView.setDataRefresh(false);
                        isLoadMore = true;
                        mAdapter.updateLoadStatus(mAdapter.LOAD_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getDatas();
                            }
                        }, 1000);
                    }
                }
            }
        });
    }
}
