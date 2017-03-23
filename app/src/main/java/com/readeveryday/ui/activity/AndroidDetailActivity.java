package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.AndroidDetailPresenter;
import com.readeveryday.ui.view.AndroidDetailView;
import com.readeveryday.utils.StatusBarUtil;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;

public class AndroidDetailActivity extends BaseActivity<AndroidDetailView, AndroidDetailPresenter> implements AndroidDetailView {

    @BindView(R.id.webview_detail)
    WebView mWebviewDetail;
    @BindView(R.id.activity_android_detail)
    RelativeLayout mActivityAndroidDetail;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    String url;
    String title;
    @BindView(R.id.collision_view)
    LoadingView mCollisionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
            setTitle(title);
            mPresenter.setData(url);
        }

    }

    @Override
    protected AndroidDetailPresenter createPresenter() {
        return new AndroidDetailPresenter(this);
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_android_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    public LoadingView getLoadingView() {
        return mCollisionView;
    }

    @Override
    public WebView getWebView() {
        return mWebviewDetail;
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
}
