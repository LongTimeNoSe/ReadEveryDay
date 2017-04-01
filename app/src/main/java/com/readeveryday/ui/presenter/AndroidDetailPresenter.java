package com.readeveryday.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.readeveryday.Constants;
import com.readeveryday.R;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.AndroidDetailView;
import com.readeveryday.utils.PromptUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import app.dinus.com.loadingdrawable.LoadingView;
import app.dinus.com.loadingdrawable.onekeyshare.OnekeyShare;

/**
 * Created by XuYanping on 2017/3/17.
 */

public class AndroidDetailPresenter extends BasePresenter<AndroidDetailView> {

    private Context mContext;
    private AndroidDetailView mView;
    private LoadingView mLoadingView;
    private WebView mWebView;
    private FloatingActionButton mCollection;
    private MyCollectDao mDao;
    private MyCollect mMyCollect;
    private String title;
    private String url;
    private String imageUrl;
    private boolean isCollected;
    private boolean isLogin;

    public AndroidDetailPresenter(Context context) {
        mContext = context;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMyCollectDao();
    }

    public void setData(String url, String title, String imageUrl) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        mView = getView();
        if (mView != null) {
            initData();
            initWebView();
            mWebView.loadUrl(url);
            initImagePath();
        }
    }

    private void initData() {

        mLoadingView = mView.getLoadingView();
        mWebView = mView.getWebView();
        mCollection = mView.getFloatingActionButton();
        isLogin = mView.isLogin();

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
                        PromptUtil.toastShowShort(mContext, "取消收藏成功");
                        isCollected = false;
                    } else {
                        mCollection.setImageResource(R.drawable.collected);
                        insertAndroidNews();
                        PromptUtil.toastShowShort(mContext, "收藏成功");
                        isCollected = true;
                    }
                } else {
                    mView.toLogin();
                }
            }
        });

    }

    private void initWebView() {

        mLoadingView.setVisibility(View.VISIBLE);
        WebSettings setting = mWebView.getSettings();

        // 网页内容的宽度是否可大于WebView控件的宽度
        setting.setLoadWithOverviewMode(false);
        //保存表单数据
        setting.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        // 启动应用缓存
        setting.setAppCacheEnabled(true);
        // 设置缓存模式
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);

        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        setting.setUseWideViewPort(true);
        // 告诉WebView启用JavaScript执行。默认的是false。
        setting.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        setting.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        setting.setDomStorageEnabled(true);
        // 排版适应屏幕
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        setting.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 缩放比例 1
        mWebView.setInitialScale(1);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressChanged(newProgress);
            }

        });
        mWebView.addJavascriptInterface(new ImageClickInterface(mContext), "injectedObject");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                addImageClickListener();
                super.onPageFinished(view, url);
            }
        });
    }


    //隐藏进度条
    public void hindProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    //显示进度条
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }


    //显示WebView
    public void showWebView() {
        mWebView.setVisibility(View.VISIBLE);
    }


    //隐藏WebView
    public void hindWebView() {
        mWebView.setVisibility(View.GONE);
    }

    /**
     * 进度条变化时调用
     */
    public void progressChanged(int newProgress) {
        if (newProgress >= 0 & newProgress < 100) {
            hindWebView();
            showProgress();
        } else if (newProgress == 100) {
            hindProgress();
            showWebView();
        }
    }

    /**
     * 添加js监听
     */
    public void addImageClickListener() {

        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                //  "objs[i].onclick=function(){alert(this.getAttribute(\"has_link\"));}" +
                "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
                "}" +
                "})()");

        // 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        mWebView.loadUrl("javascript:(function(){" +
                "var objs =document.getElementsByTagName(\"a\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){" +
                "window.injectedObject.textClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
                "}" +
                "})()");

    }

    public class ImageClickInterface {

        Context mContext;

        public ImageClickInterface(Context context) {
            mContext = context;
        }
    }

    //数据库增加
    public void insertAndroidNews() {
        mMyCollect = new MyCollect(userName, "", "", title, imageUrl, url, "", Constants.FROMANDROID);
        mDao.insert(mMyCollect);
    }

    //数据库查询
    public List<MyCollect> queryAndroidNews() {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.NewsTitle.eq(title)).build().list();
        return list;
    }

    //数据库删除
    public void deleteAndroidNews() {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.NewsTitle.eq(title)).build().list();
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
        if (imageUrl != null) {
            share.setImageUrl(imageUrl);
        } else {
            share.setImagePath(localImage);
        }
        share.setUrl(url);
        share.setSite(mContext.getString(R.string.app_name));
        share.setSiteUrl(url);
        share.show(mContext);
    }

    private String localImage;

    private void initImagePath() {
        try {// 判断SD卡中是否存在此文件夹
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && Environment.getExternalStorageDirectory().exists()) {
                localImage = Environment.getExternalStorageDirectory().getAbsolutePath() + "/localImage.png";
            } else {
                localImage = mContext.getFilesDir().getAbsolutePath() + "/localImage.png";
            }
            File file = new File(localImage);
            // 判断图片是否存此文件夹中
            if (!file.exists()) {
                file.createNewFile();
                Bitmap pic = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.app_launcher);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            localImage = null;
        }
    }
}
