package com.readeveryday.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseFragment;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.presenter.DailyPresenter;
import com.readeveryday.ui.view.DailyFgView;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class DailyFragment extends BaseFragment<DailyFgView, DailyPresenter> implements DailyFgView {
    @Override
    protected DailyPresenter createPresenter() {
        return new DailyPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return null;
    }
}
