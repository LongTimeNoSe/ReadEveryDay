package com.readeveryday.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by XuYanping on 2017/3/15.
 */

public interface ZhiHuFgView {

    void setDataRefresh(Boolean refresh);

    RecyclerView getRecyclerView();

    LinearLayoutManager getLayoutManager();

}
