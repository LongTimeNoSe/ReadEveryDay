package com.readeveryday.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;

import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.adapter.CollectAdapter;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.CollectView;
import com.readeveryday.utils.MyItemTouchHelper;
import com.readeveryday.utils.SimpleItemTouchHelperCallback;

import java.util.List;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectPresenter extends BasePresenter<CollectView> {

    private Context mContext;
    private CollectView mView;
    private RecyclerView mRecyclerView;
    private CollectAdapter mAdapter;
    private List<MyCollect> mList;
    private MyCollectDao mDao;
    private RelativeLayout mNoData;
    private MyItemTouchHelper mHelper;
    private SimpleItemTouchHelperCallback mCallback;

    public CollectPresenter(Context context) {
        mContext = context;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMyCollectDao();
        mList = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).build().list();
    }

    public void setData() {
        mView = getView();
        if (mView != null) {
            mRecyclerView = mView.getRecyclerView();
            mNoData = mView.getNoData();

            if (mList == null) {
                return;
            }
            if (mList.size() == 0) {
                mRecyclerView.setVisibility(View.GONE);
                mNoData.setVisibility(View.VISIBLE);
            } else {
                mAdapter = new CollectAdapter(mContext, mList);
                mRecyclerView.setAdapter(mAdapter);
                mHelper = new MyItemTouchHelper() {
                    @Override
                    public void onItemDissmiss(int position) {

                        deleteAndroidNews(mList.get(position).getNewsTitle());
                        mList.remove(position);
                        mAdapter.notifyItemRemoved(position);
//                        mAdapter.notifyDataSetChanged();
                        setData();
                    }
                };
                mCallback = new SimpleItemTouchHelperCallback(mHelper);
                ItemTouchHelper helper = new ItemTouchHelper(mCallback);
                helper.attachToRecyclerView(mRecyclerView);
            }
        }
    }

    //数据库删除
    public void deleteAndroidNews(String str) {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.NewsTitle.eq(str)).build().list();
        for (MyCollect item : list) {
            mDao.delete(item);
        }
    }
}
