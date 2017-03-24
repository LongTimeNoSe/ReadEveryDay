package com.readeveryday.ui.presenter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.readeveryday.bean.gank.Gank;
import com.readeveryday.bean.gank.Meizhi;
import com.readeveryday.manager.CardLayoutManager;
import com.readeveryday.ui.adapter.MeiZhiAdapter;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.MeiZhiView;
import com.readeveryday.utils.CardConfig;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.TanTanCallback;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XuYanping on 2017/3/20.
 */

public class MeiZhiPresenter extends BasePresenter<MeiZhiView> {

    private Context mContext;
    private MeiZhiView mView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButton;
    private CardLayoutManager mLayoutManager;
    private MeiZhiAdapter mAdapter;
    private List<Gank> mList;
    int page = 1;

    public MeiZhiPresenter(Context context) {
        mContext = context;
    }

    public void getData() {

        mView = getView();
        if (mView != null) {
            mRecyclerView = mView.getRecyclerView();
            mButton = mView.getFloatingActionButton();
            mLayoutManager = mView.getCardLayoutManager();
            mRecyclerView.setLayoutManager(mLayoutManager);
            gankApi.getMeizhiData(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Meizhi>() {
                @Override
                public void onCompleted() {
                    mView.setDataRefresh(false);
                }

                @Override
                public void onError(Throwable e) {
                    mView.setDataRefresh(false);
                }

                @Override
                public void onNext(Meizhi meizhi) {
                    if (meizhi.getResults() == null) {
                        PromptUtil.toastShowShort(mContext, "没有咯...");
                        return;
                    }
                    setData(mContext, meizhi.getResults(), mView, mRecyclerView);
                }
            });

            mButton.setOnClickListener(mListener);
        }
    }

    private void setData(final Context context, List<Gank> results, MeiZhiView view, final RecyclerView recyclerView) {

        if (page > 1) {
            mList.clear();
            mList.addAll(results);
            mAdapter.notifyDataSetChanged();
        } else {

            mList = results;
            mAdapter = new MeiZhiAdapter(context, results);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        view.setDataRefresh(false);
        CardConfig.initConfig(context);
        final TanTanCallback callback = new TanTanCallback(recyclerView, mAdapter, mList);

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            page += 1;
            getData();
        }
    };
}
