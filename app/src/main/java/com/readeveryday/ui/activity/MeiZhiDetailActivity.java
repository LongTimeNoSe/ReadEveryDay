package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.MeiZhiDetailPresenter;
import com.readeveryday.ui.view.MeiZhiDetailView;
import com.readeveryday.utils.StatusBarUtil;
import com.readeveryday.widget.ArcMenu;

import butterknife.BindView;

public class MeiZhiDetailActivity extends BaseActivity<MeiZhiDetailView, MeiZhiDetailPresenter> implements MeiZhiDetailView {

    @BindView(R.id.iv_meizhi)
    ImageView mIvMeizhi;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.meizhi_detail_edit)
    ArcMenu mMeizhiDetailEdit;
    @BindView(R.id.iv_collect_meizhi)
    ImageView mIvCollectMeizhi;

    private String url;
    private String desc;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("妹子哟");
        isLogin = mSharedPreferences.getBoolean("isLoging", false);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        url = intent.getStringExtra("url");
        desc = intent.getStringExtra("imageDesc");
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
        mPresenter.setData(url, desc);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = mSharedPreferences.getBoolean("isLoging", false);
        mPresenter.setData(url, desc);
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
    public String getActivityTag() {
        return "MeiZhiDetailActivity";
    }

    @Override
    public ImageView getImageView() {
        return mIvMeizhi;
    }

    @Override
    public ArcMenu getMenu() {
        return mMeizhiDetailEdit;
    }

    @Override
    public ImageView getCollectImage() {
        return mIvCollectMeizhi;
    }

    @Override
    public void toLogin() {
        startActivity(LoginActivity.class);
    }

    @Override
    public boolean isLogin() {
        return isLogin;
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
