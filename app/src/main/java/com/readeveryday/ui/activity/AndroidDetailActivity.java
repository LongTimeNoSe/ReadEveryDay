package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
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
    String imageUrl;
    @BindView(R.id.collision_view)
    LoadingView mCollisionView;
    @BindView(R.id.collection)
    FloatingActionButton mCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
            imageUrl = intent.getStringExtra("imageUrl");
            setTitle(title);
            mPresenter.setData(url, title, imageUrl);
        }

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
//                        PromptUtil.toastShowShort(AndroidDetailActivity.this,"分享");
                        mPresenter.share();
                        break;
                }
                return AndroidDetailActivity.super.onOptionsItemSelected(item);
            }
        });
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
    public String getActivityTag() {
        return "AndroidDetailActivity";
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
    public FloatingActionButton getFloatingActionButton() {
        return mCollection;
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
