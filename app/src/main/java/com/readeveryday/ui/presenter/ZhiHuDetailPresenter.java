package com.readeveryday.ui.presenter;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.Constants;
import com.readeveryday.R;
import com.readeveryday.bean.zhihu.News;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.ZhiHuDetailView;
import com.readeveryday.utils.PromptUtil;

import java.util.List;

import app.dinus.com.loadingdrawable.LoadingView;
import app.dinus.com.loadingdrawable.onekeyshare.OnekeyShare;
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
    private FloatingActionButton mCollection;
    private TextView mDesc;

    private MyCollectDao mDao;
    private MyCollect mMyCollect;
    private String title;
    private String newsTitle;
    private String newsId;
    private String newsImageUrl;
    private String newsUrl;
    private String url;
    private boolean isCollected;
    private boolean isLogin;

    public ZhiHuDetailPresenter(Context context) {
        mContext = context;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMyCollectDao();
    }

    public void initView(String id, String title, String newsImageUrl) {
        this.newsId = id;
        this.newsTitle = title;
        this.newsImageUrl = newsImageUrl;
        mView = getView();
        if (mView != null) {
            initDat();
            loadingData(id);
        }
    }

    private void initDat() {
        mLoadingView = mView.getLoadingView();
        mWebView = mView.getWebView();
        mToolbar = mView.getThisToolbar();
        mTopImage = mView.getTopImageView();
        mCollection = mView.getFloatingActionButton();
        isLogin = mView.isLogin();
        mDesc = mView.getTopDescribe();
        if (isLogin) {
            if (queryAndroidNews() != null && queryAndroidNews().size() > 0) {
                mCollection.setImageResource(R.drawable.collected);
                isCollected = true;
            } else {
                mCollection.setImageResource(R.drawable.collection);
                isCollected = false;
            }
        }

        mCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    if (isCollected) {
                        mCollection.setImageResource(R.drawable.collection);
                        deleteAndroidNews();
                        isCollected = false;
                        PromptUtil.toastShowShort(mContext, "取消收藏成功");
                    } else {
                        mCollection.setImageResource(R.drawable.collected);
                        insertAndroidNews();
                        isCollected = true;
                        PromptUtil.toastShowShort(mContext, "收藏成功");
                    }
                } else {
                    mView.toLogin();
                }
            }
        });

    }

    public void loadingData(String id) {

        zhihuApi.getDetailNews(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<News>() {
            @Override
            public void onCompleted() {
                initWebView();
            }

            @Override
            public void onError(Throwable e) {
                if (e.toString().contains("404")) {
                    PromptUtil.toastShowShort(mContext, "讶，404啦");
                }else{
                    PromptUtil.toastShowShort(mContext, e.toString());
                }
            }

            @Override
            public void onNext(News news) {
                setDatas(news);
            }
        });
    }

    private void setDatas(News news) {

        url = news.getShare_url();
        String head = "<head>\n" + "\t<link rel=\"stylesheet\" href=\"" + news.getCss()[0] + "\"/>\n" + "</head>";
        String img = "<div class=\"headline\">";
        newsUrl = head + news.getBody().replace(img, " ");
        mWebView.loadDataWithBaseURL(null, newsUrl, "text/html", "utf-8", null);
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

    //数据库增加
    public void insertAndroidNews() {
        mMyCollect = new MyCollect(userName, "", "", newsTitle, newsImageUrl, newsUrl, newsId, Constants.FROMZHIHU);
        mDao.insert(mMyCollect);
    }

    //数据库查询
    public List<MyCollect> queryAndroidNews() {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.NewsTitle.eq(newsTitle)).build().list();
        return list;
    }

    //数据库删除
    public void deleteAndroidNews() {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.NewsTitle.eq(newsTitle)).build().list();
        for (MyCollect item : list) {
            mDao.delete(item);
        }
    }

    public void share() {

        OnekeyShare share = new OnekeyShare();
        share.disableSSOWhenAuthorize();
        share.setTitle(mContext.getString(R.string.app_name));
        share.setTitleUrl(url);
        share.setText(mContext.getString(R.string.share_content));
        share.setImageUrl(newsImageUrl);
        share.setUrl(newsUrl);
        share.setSite(mContext.getString(R.string.app_name));
        share.setSiteUrl(newsUrl);
        share.show(mContext);

    }
}
