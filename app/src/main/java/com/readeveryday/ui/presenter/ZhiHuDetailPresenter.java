package com.readeveryday.ui.presenter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.R;
import com.readeveryday.bean.zhihu.News;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.ZhiHuDetailView;

import app.dinus.com.loadingdrawable.LoadingView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XuYanping on 2017/3/20.
 */

public class ZhiHuDetailPresenter extends BasePresenter<ZhiHuDetailView> {

    private Context mContext;
    private ZhiHuDetailView mView;
    private LoadingView mLoadingView;
    private WebView mWebView;
    private Toolbar mToolbar;
    private ImageView mTopImage;
    private TextView mDesc;
    private String title;

    public ZhiHuDetailPresenter(Context context) {
        mContext = context;
    }

    public void initView(String id) {

        mView = getView();
        if (mView != null) {
            mLoadingView = mView.getLoadingView();
            mWebView = mView.getWebView();
            mToolbar = mView.getThisToolbar();
            mTopImage = mView.getTopImageView();
            mDesc = mView.getTopDescribe();
            loadingData(id);
        }
    }

    public void loadingData(String id) {

        zhihuApi.getDetailNews(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<News>() {
            @Override
            public void onCompleted() {
                initWebView();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(News news) {
                setDatas(news);
            }
        });
    }

    private void setDatas(News news) {

        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + news.getCss()[0] + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html = head + news.getBody().replace(img, " ");
        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        mToolbar.setTitle(news.getTitle());
        title = news.getTitle();
        mDesc.setText(news.getImage_source());
        Glide.with(mContext).load(news.getImage()).centerCrop().error(R.drawable.loder_error).into(mTopImage);
    }


    private void initWebView() {

        WebSettings setting = mWebView.getSettings();
        // 告诉WebView启用JavaScript执行。默认的是false。
        setting.setJavaScriptEnabled(true);
        // 排版适应屏幕
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressChanged(newProgress);
            }
        });
    }

    private void progressChanged(int newProgress) {

        if (newProgress >= 0 && newProgress < 100) {
            mLoadingView.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        } else if (newProgress == 100) {
            mLoadingView.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public String setTitle() {
        return title;
    }
}
