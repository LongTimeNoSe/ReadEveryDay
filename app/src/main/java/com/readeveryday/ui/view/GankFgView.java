package com.readeveryday.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by XuYanping on 2017/3/15.
 */

public interface GankFgView {
    void setDataRefresh(Boolean refresh);

    GridLayoutManager getLayoutManager();

    RecyclerView getRecyclerView();
}
