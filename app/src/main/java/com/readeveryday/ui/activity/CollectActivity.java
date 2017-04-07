package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.CollectPresenter;
import com.readeveryday.ui.view.CollectView;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectActivity extends BaseActivity<CollectView, CollectPresenter> implements CollectView {
    @BindView(R.id.rv_collect)
    RecyclerView mRvCollect;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerLayout_collect)
    LinearLayout mParentLayoutCollect;
    @BindView(R.id.no_data)
    RelativeLayout mNoData;
    private LinearLayoutManager mLayoutManager;

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
    public LinearLayout getParentLayout() {
        return mParentLayoutCollect;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRvCollect;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public RelativeLayout getNoData() {
        return mNoData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("收藏");
        PromptUtil.snackbarShow(mParentLayoutCollect, "左滑可取消收藏哦~");
        mLayoutManager = new LinearLayoutManager(this);
        mRvCollect.setLayoutManager(mLayoutManager);
        mPresenter.setData();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setData();
    }

    @Override
    public String getActivityTag() {
        return "CollectActivity";
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
    }
}
