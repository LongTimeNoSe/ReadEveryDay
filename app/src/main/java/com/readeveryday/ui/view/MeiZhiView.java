package com.readeveryday.ui.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import com.readeveryday.manager.CardLayoutManager;

/**
 * Created by XuYanping on 2017/3/20.
 */

public interface MeiZhiView {

    RecyclerView getRecycleView();

    FloatingActionButton getFloatingActionButton();

    CardLayoutManager getCardLayoutManager();

}
