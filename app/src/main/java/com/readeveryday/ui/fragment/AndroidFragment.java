package com.readeveryday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.readeveryday.R;
import com.readeveryday.base.BaseFragment;
import com.readeveryday.ui.presenter.AndroidPresenter;
import com.readeveryday.ui.view.AndroidView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class AndroidFragment extends BaseFragment<AndroidView, AndroidPresenter> implements AndroidView {
    @BindView(R.id.content_list)
    RecyclerView mContentList;

    private LinearLayoutManager mLayoutManager;

    @Override
    protected AndroidPresenter createPresenter() {
        return new AndroidPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    public String getFragmentTag() {
        return "AndroidFragment";
    }

//    @Override
//    public String getFragmentTag() {
//        return "AndroidFragment";
//    }

    @Override
    public RecyclerView getRecyclerView() {
        return mContentList;
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDataRefresh(true);
        mPresenter.getDatas();
        mPresenter.onScrollRecycleView();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getDatas();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mContentList.setLayoutManager(mLayoutManager);
    }

}
