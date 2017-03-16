package com.readeveryday.ui.presenter;

import android.content.Context;

import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.DailyFgView;

/**
 * Created by XuYanping on 2017/3/16.
 */

public class DailyPresenter extends BasePresenter<DailyFgView> {

    private Context mContext;

    public DailyPresenter(Context context) {
        mContext = context;
    }
}
