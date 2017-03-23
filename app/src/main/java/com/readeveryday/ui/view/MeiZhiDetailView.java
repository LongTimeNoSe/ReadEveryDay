package com.readeveryday.ui.view;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by XuYanping on 2017/3/22.
 */

public interface MeiZhiDetailView {

    RelativeLayout getParentLayout();

    ImageView getImageView();

    CoordinatorLayout getCoordinatorLayout();

    FloatingActionButton getFloatingActionButton();
}
