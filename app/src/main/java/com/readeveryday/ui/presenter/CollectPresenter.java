package com.readeveryday.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.CollectView;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectPresenter extends BasePresenter<CollectView> {

    private Context mContext;
    private RecyclerView mRecyclerView;

    public CollectPresenter(Context context) {
        mContext = context;
    }



}
