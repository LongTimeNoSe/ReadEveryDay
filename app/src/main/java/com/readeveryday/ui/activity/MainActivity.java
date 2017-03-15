package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.readeveryday.R;
import com.readeveryday.ui.adapter.FragmentAdapter;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.base.BaseFragment;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.fragment.DailyFragment;
import com.readeveryday.ui.fragment.GankFragment;
import com.readeveryday.ui.fragment.ZhiHuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.content_viewPager)
    ViewPager mContentViewPager;

    List<BaseFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initTabView();
    }

    private void initTabView() {
        mFragmentList = new ArrayList<BaseFragment>();
        mFragmentList.add(new ZhiHuFragment());
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new DailyFragment());
        mContentViewPager.setOffscreenPageLimit(3);//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        mContentViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mFragmentList, "main_view_pager"));
        mTabLayout.setupWithViewPager(mContentViewPager);
    }
}
