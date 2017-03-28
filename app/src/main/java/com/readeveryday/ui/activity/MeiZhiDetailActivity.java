package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.MeiZhiDetailPresenter;
import com.readeveryday.ui.view.MeiZhiDetailView;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.StatusBarUtil;

import butterknife.BindView;

public class MeiZhiDetailActivity extends BaseActivity<MeiZhiDetailView, MeiZhiDetailPresenter> implements MeiZhiDetailView {

    @BindView(R.id.iv_meizhi)
    ImageView mIvMeizhi;
    @BindView(R.id.fab_down_meizhi)
    FloatingActionButton mFabDownMeizhi;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_mei_zhi_detail)
    RelativeLayout mActivityMeiZhiDetail;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    private String url;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("妹子哟");
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        url = intent.getStringExtra("url");
        desc = intent.getStringExtra("imageDesc");
        mPresenter.setData(url, desc);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
//                        PromptUtil.toastShowShort(MeiZhiDetailActivity.this,"分享");
                        mPresenter.share();
                        break;
                }
                return MeiZhiDetailActivity.super.onOptionsItemSelected(item);
            }
        });
    }

    @Override
    protected MeiZhiDetailPresenter createPresenter() {
        return new MeiZhiDetailPresenter(this);
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_mei_zhi_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public RelativeLayout getParentLayout() {
        return mActivityMeiZhiDetail;
    }

    @Override
    public ImageView getImageView() {
        return mIvMeizhi;
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    @Override
    public FloatingActionButton getFloatingActionButton() {
        return mFabDownMeizhi;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_right_menu, menu);
        return true;
    }
}
