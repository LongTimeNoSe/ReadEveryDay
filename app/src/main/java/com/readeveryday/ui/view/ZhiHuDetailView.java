package com.readeveryday.ui.view;

import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import app.dinus.com.loadingdrawable.LoadingView;

/**
 * Created by XuYanping on 2017/3/20.
 */

public interface ZhiHuDetailView {

    WebView getWebView();

    ImageView getTopImageView();

    TextView getTopDescribe();

    LoadingView getLoadingView();

    Toolbar getThisToolbar();
}
