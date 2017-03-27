package com.readeveryday.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.CollectPresenter;
import com.readeveryday.ui.view.CollectView;
import com.readeveryday.utils.PromptUtil;

import butterknife.BindView;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectActivity extends BaseActivity<CollectView, CollectPresenter> implements CollectView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.rv_collect)
    RecyclerView mRvCollect;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerLayout_collect)
    DrawerLayout mDrawerLayoutCollect;
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayoutCollect, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayoutCollect.setDrawerListener(toggle);
        toggle.syncState();
        mLayoutManager = new LinearLayoutManager(this);
        mRvCollect.setLayoutManager(mLayoutManager);
        mPresenter.setData();
        mNavView.setItemIconTintList(null);
        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notice");
            builder.setMessage("Are You Sure Exit ?");
            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.show();
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                startActivity(MainActivity.class);
                this.finish();
                break;
            case R.id.nav_about_me:
                PromptUtil.snackbarShow(mDrawerLayoutCollect, "关于我");
                break;
            case R.id.nav_about_read_everyday:
                PromptUtil.snackbarShow(mDrawerLayoutCollect, "关于ReadEveryDay");
                break;

        }
        mDrawerLayoutCollect.closeDrawers();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setData();
    }
}
