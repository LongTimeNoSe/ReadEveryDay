package com.readeveryday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseFragment;
import com.readeveryday.ui.presenter.GankPresenter;
import com.readeveryday.ui.view.GankFgView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class GankFragment extends BaseFragment<GankFgView, GankPresenter> implements GankFgView {

    @BindView(R.id.content_list)
    RecyclerView mContentList;
    private GridLayoutManager mLayoutManager;

    @Override
    protected GankPresenter createPresenter() {
        return new GankPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    public String getFragmentTag() {
        return "GankFragment";
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mContentList;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mContentList.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDataRefresh(true);
        mPresenter.getGankData();
        mPresenter.scrollRecycleView();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getGankData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
