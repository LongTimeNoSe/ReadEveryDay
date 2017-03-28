package com.readeveryday.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.readeveryday.R;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.MeiZhiDetailView;
import com.readeveryday.utils.PromptUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import app.dinus.com.loadingdrawable.onekeyshare.OnekeyShare;

/**
 * Created by XuYanping on 2017/3/22.
 */

public class MeiZhiDetailPresenter extends BasePresenter<MeiZhiDetailView> {

    private Context mContext;
    private MeiZhiDetailView mView;
    private ImageView mImageView;
    private FloatingActionButton mButton;
    private RelativeLayout mLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private String desc;
    private String imageUrl;

    public MeiZhiDetailPresenter(Context context) {
        mContext = context;
    }

    public void setData(String url, String desc) {
        mView = getView();
        this.imageUrl = url;
        if (mView != null) {
            this.desc = desc;
            mImageView = mView.getImageView();
            mButton = mView.getFloatingActionButton();
            mLayout = mView.getParentLayout();
            mCoordinatorLayout = mView.getCoordinatorLayout();
            Glide.with(mContext).load(url).error(R.drawable.loder_error).into(mImageView);
            mButton.setOnClickListener(saveImageClickListener);
            initImagePath();
        }
    }

    View.OnClickListener saveImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mImageView.buildDrawingCache();
            Bitmap bitmap = mImageView.getDrawingCache();
            //将Bitmap转化为二进制存入本地
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ReadEveryDay");
            if (!file.exists()) {
                file.mkdirs();
            }
            File imageFile = new File(file, desc + "妹子.png");
            try {
                FileOutputStream out = new FileOutputStream(imageFile);
                out.write(byteArray, 0, byteArray.length);
                out.flush();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(imageFile);
                intent.setData(uri);
                mContext.sendBroadcast(intent);
                PromptUtil.snackbarShow(mCoordinatorLayout, "保存成功");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("e", e.toString());
            }
        }
    };


    public void share() {

        OnekeyShare share = new OnekeyShare();
        share.disableSSOWhenAuthorize();
        share.setTitle(mContext.getString(R.string.app_name));
        share.setTitleUrl(imageUrl);
        share.setText(mContext.getString(R.string.share_content));
        if (imageUrl != null) {
            share.setImageUrl(imageUrl);
        } else {
            share.setImagePath(localImage);
        }
        share.setUrl(imageUrl);
        share.setSite(mContext.getString(R.string.app_name));
        share.setSiteUrl(imageUrl);
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
