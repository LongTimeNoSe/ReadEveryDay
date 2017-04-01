package com.readeveryday.ui.view;

import android.widget.ImageView;

import com.readeveryday.widget.ArcMenu;

/**
 * Created by XuYanping on 2017/3/22.
 */

public interface MeiZhiDetailView {

    ImageView getImageView();

    ArcMenu getMenu();

    ImageView getCollectImage();

    void toLogin();

    boolean isLogin();

}
