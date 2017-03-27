package com.readeveryday.ui.view;

import android.support.design.widget.FloatingActionButton;
import android.webkit.WebView;
import android.widget.ProgressBar;

import app.dinus.com.loadingdrawable.LoadingView;

/**
 * Created by XuYanping on 2017/3/17.
 */

public interface AndroidDetailView {

    //加载动画
    LoadingView getLoadingView();

    //获取WebView
    WebView getWebView();

    //收藏
    FloatingActionButton getFloatingActionButton();

}
