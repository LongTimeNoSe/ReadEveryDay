package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.ZhiHuDetailPresenter;
import com.readeveryday.ui.view.ZhiHuDetailView;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.StatusBarUtil;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;

public class ZhiHuDetailActivity extends BaseActivity<ZhiHuDetailView, ZhiHuDetailPresenter> implements ZhiHuDetailView {

    @BindView(R.id.detail_bar_image)
    ImageView mDetailBarImage;
    @BindView(R.id.zhihu_detail_bar_copyright)
    TextView mZhihuDetailBarCopyright;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.wb_zhihu_detail)
    WebView mWbZhihuDetail;
    @BindView(R.id.collision_view)
    LoadingView mCollisionView;
    @BindView(R.id.activity_zhi_hu_detail)
    CoordinatorLayout mActivityZhiHuDetail;
    @BindView(R.id.collection)
    FloatingActionButton mCollection;

    private String id;
    private String title;
    private String newsImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("newsId");
            title = intent.getStringExtra("title");
            newsImageUrl = intent.getStringExtra("newsImageUrl");
        }
        mPresenter.initView(id, title, newsImageUrl);
        setTitle(mPresenter.setTitle());

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
                        mPresenter.share();
                        break;
                }
                return ZhiHuDetailActivity.super.onOptionsItemSelected(item);
            }
        });
    }

    @Override
    protected ZhiHuDetailPresenter createPresenter() {
        return new ZhiHuDetailPresenter(this);
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_zhi_hu_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public String getActivityTag() {
        return "ZhiHuDetailActivity";
    }

    @Override
    public WebView getWebView() {
        return mWbZhihuDetail;
    }

    @Override
    public ImageView getTopImageView() {
        return mDetailBarImage;
    }

    @Override
    public TextView getTopDescribe() {
        return mZhihuDetailBarCopyright;
    }

    @Override
    public LoadingView getLoadingView() {
        return mCollisionView;
    }

    @Override
    public Toolbar getThisToolbar() {
        return mToolbar;
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
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_right_menu, menu);
        return true;
    }
}
