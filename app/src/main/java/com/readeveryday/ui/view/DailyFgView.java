package com.readeveryday.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by XuYanping on 2017/3/16.
 */

public interface DailyFgView {
    RecyclerView getRecyclerView();

    void setDataRefresh(Boolean refresh);

    LinearLayoutManager getLayoutManager();

}
