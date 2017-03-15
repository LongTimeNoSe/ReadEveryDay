package com.readeveryday.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseFragment;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.presenter.GankPresenter;
import com.readeveryday.ui.view.GankFgView;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class GankFragment extends BaseFragment<GankFgView, GankPresenter> implements GankFgView {
    @Override
    protected GankPresenter createPresenter() {
        return new GankPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    public void setDataRefresh(Boolean refresh) {

    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return null;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }
}
