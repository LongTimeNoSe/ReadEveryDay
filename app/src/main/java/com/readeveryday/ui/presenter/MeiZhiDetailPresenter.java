package com.readeveryday.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.readeveryday.Constants;
import com.readeveryday.R;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.MeiZhiDetailView;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.widget.ArcMenu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import app.dinus.com.loadingdrawable.onekeyshare.OnekeyShare;

/**
 * Created by XuYanping on 2017/3/22.
 */

public class MeiZhiDetailPresenter extends BasePresenter<MeiZhiDetailView> {

    private Context mContext;
    private MeiZhiDetailView mView;
    private ImageView mImageView;
    private ImageView mCollectImage;
    private ArcMenu mMeizhiDetailEdit;
    private MyCollectDao mDao;
    private MyCollect mMyCollect;

    private String imageUrl;
    private String imageDesc;
    private boolean isCollected;
    private boolean isLogin;

    public MeiZhiDetailPresenter(Context context) {
        mContext = context;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMyCollectDao();
    }

    public void setData(String url, String desc) {
        this.imageUrl = url;
        this.imageDesc = desc;
        mView = getView();
        if (mView != null) {
            mImageView = mView.getImageView();
            mMeizhiDetailEdit = mView.getMenu();
            mCollectImage = mView.getCollectImage();
            isLogin = mView.isLogin();
            Glide.with(mContext).load(url).error(R.drawable.loder_error).into(mImageView);
            initCollectState();
            mMeizhiDetailEdit.setOnMenuItemClickListener(new ArcMenu.onMenuItemClickListener() {
                @Override
                public void onclick(View view, int position) {
                    switch (view.getId()) {

                        case R.id.rl_down_meizhi:
//                            PromptUtil.toastShowShort(mContext, "down");
                            downMeiZhi();
                            break;
                        case R.id.rl_collect_meizhi:
//                            PromptUtil.toastShowShort(mContext, "collect");
                            if (isLogin) {
                                if (isCollected) {
                                    deleteMeiZhi();
                                    mCollectImage.setImageResource(R.drawable.collection);
                                    isCollected = false;
                                } else {
                                    insertMeiZhi();
                                    mCollectImage.setImageResource(R.drawable.collected);
                                    isCollected = true;
                                }
                            } else {
                                mView.toLogin();
                            }

                            break;

                    }
                }
            });
        }
    }

    public void downMeiZhi() {

        initImagePath();
        mImageView.buildDrawingCache();
        Bitmap bitmap = mImageView.getDrawingCache();
        //将Bitmap转化为二进制存入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] byteArray = stream.toByteArray();
        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ReadEveryDay");
        if (!file.exists()) {
            file.mkdirs();
        }
        File imageFile = new File(file, imageDesc + "妹子.png");
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            out.write(byteArray, 0, byteArray.length);
            out.flush();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(imageFile);
            intent.setData(uri);
            mContext.sendBroadcast(intent);
            PromptUtil.toastShowShort(mContext, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("e", e.toString());
        }

    }

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


    //数据库增加
    public void insertMeiZhi() {
        mMyCollect = new MyCollect(userName, imageUrl, imageDesc, "", "", "", "", Constants.FROMMEIZHI);
        mDao.insert(mMyCollect);
    }

    //数据库查询
    public List<MyCollect> queryMeiZhi() {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.MeiZhiImageDesc.eq(imageDesc)).build().list();
        return list;
    }

    //数据库删除
    public void deleteMeiZhi() {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.MeiZhiImageDesc.eq(imageDesc)).build().list();
        for (MyCollect item : list) {
            mDao.delete(item);
        }
    }

    private void initCollectState() {

        if (isLogin) {
            if (queryMeiZhi() != null && queryMeiZhi().size() > 0) {
                mCollectImage.setImageResource(R.drawable.collected);
                isCollected = true;
            } else {
                mCollectImage.setImageResource(R.drawable.collection);
                isCollected = false;
            }
        }
    }
}
