package com.readeveryday.ui.view;

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

}
