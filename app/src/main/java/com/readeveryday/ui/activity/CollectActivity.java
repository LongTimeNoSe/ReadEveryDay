package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.CollectPresenter;
import com.readeveryday.ui.view.CollectView;

import butterknife.BindView;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectActivity extends BaseActivity<CollectView, CollectPresenter> implements CollectView {
    @BindView(R.id.rv_collect)
    RecyclerView mRvCollect;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected CollectPresenter createPresenter() {
        return new CollectPresenter(this);
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRvCollect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("收藏");
    }

    @Override
    public boolean canBack() {
        return true;
    }
}
