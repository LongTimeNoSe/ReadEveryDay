package com.readeveryday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.readeveryday.R;
import com.readeveryday.manager.CardLayoutManager;
import com.readeveryday.ui.base.BaseFragment;
import com.readeveryday.ui.presenter.MeiZhiPresenter;
import com.readeveryday.ui.view.MeiZhiView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/20.
 */

public class MeiZhiFragment extends BaseFragment<MeiZhiView, MeiZhiPresenter> implements MeiZhiView {
    @BindView(R.id.rv_beauty)
    RecyclerView mRvBeauty;
    @BindView(R.id.bt_beauty_next)
    FloatingActionButton mBtBeautyNext;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getData();
    }

    @Override
    protected MeiZhiPresenter createPresenter() {
        return new MeiZhiPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_meizhi;
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRvBeauty;
    }

    @Override
    public FloatingActionButton getFloatingActionButton() {
        return mBtBeautyNext;
    }

    @Override
    public CardLayoutManager getCardLayoutManager() {
        return new CardLayoutManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
