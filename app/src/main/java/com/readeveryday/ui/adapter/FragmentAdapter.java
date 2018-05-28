package com.readeveryday.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.readeveryday.base.BaseFragment;

import java.util.List;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private String tag;
    List<BaseFragment> mFragmentList;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String tag) {
        super(fm);
        this.tag = tag;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (mFragmentList != null) return mFragmentList.size();
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (tag.equals("main_view_pager")) {

            switch (position) {
                case 0:
                    return "ZhiHu";
                case 1:
//                    return "Gank";
                    return "MeiZhi";
                case 2:
                    return "Android";
            }
        }

        return null;
    }
}
