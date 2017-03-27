package com.readeveryday.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.readeveryday.R;
import com.readeveryday.ui.adapter.FragmentAdapter;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.base.BaseFragment;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.fragment.AndroidFragment;
import com.readeveryday.ui.fragment.MeiZhiFragment;
import com.readeveryday.ui.fragment.ZhiHuFragment;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.content_viewPager)
    ViewPager mContentViewPager;

    List<BaseFragment> mFragmentList;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawerLayout_main)
    DrawerLayout mDrawerLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayoutMain, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayoutMain.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setItemIconTintList(null);
        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_main;
    }

    private void initTabView() {
        mFragmentList = new ArrayList<BaseFragment>();
        mFragmentList.add(new ZhiHuFragment());
//        mFragmentList.add(new GankFragment());
        mFragmentList.add(new MeiZhiFragment());
        mFragmentList.add(new AndroidFragment());
        mContentViewPager.setOffscreenPageLimit(3);//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        mContentViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mFragmentList, "main_view_pager"));
        mTabLayout.setupWithViewPager(mContentViewPager);
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
//        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayoutMain, getResources().getColor(R.color.colorPrimary), 0);
//        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayoutMain, getResources().getColor(R.color.colorPrimary));
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

//            case R.id.nav_home:
//                if (isForeground(this, "com.readeveryday.ui.activity.MainActivity")) {
//                    break;
//                }
//                startActivity(MainActivity.class);
//                break;
            case R.id.nav_collection:
//                PromptUtil.snackbarShow(mActivityMain, "收藏");
                startActivity(CollectActivity.class);
                this.finish();
                break;
            case R.id.nav_about_me:
//                PromptUtil.snackbarShow(mActivityMain, "关于我");
                startActivity(AboutMeActivity.class);
                this.finish();
                break;
            case R.id.nav_about_read_everyday:
                PromptUtil.snackbarShow(mActivityMain, "关于ReadEveryDay");
                startActivity(AboutReadEveryDayActivity.class);
                break;

        }
        mDrawerLayoutMain.closeDrawers();
        return true;
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
}
