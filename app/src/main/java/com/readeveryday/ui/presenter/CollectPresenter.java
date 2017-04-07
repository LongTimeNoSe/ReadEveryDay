package com.readeveryday.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.readeveryday.Constants;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.adapter.CollectAdapter;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.CollectView;
import com.readeveryday.utils.MyItemTouchHelper;
import com.readeveryday.utils.SimpleItemTouchHelperCallback;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectPresenter extends BasePresenter<CollectView> {

    private Context mContext;
    private CollectView mView;
    private RecyclerView mRecyclerView;
    private LinearLayout mParentLayout;
    private CollectAdapter mAdapter;
    private List<MyCollect> mList;
    private MyCollectDao mDao;
    private RelativeLayout mNoData;
    private MyItemTouchHelper mHelper;
    private SimpleItemTouchHelperCallback mCallback;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.STATE_ZERO:
                    int position = msg.arg1;
                    if (!mList.get(position).getMeiZhiImageDesc().equals("")) {
                        deleteMeiZhi(mList.get(position).getMeiZhiImageDesc());
                    } else if (!mList.get(position).getNewsTitle().toString().equals("")) {
                        deleteNews(mList.get(position).getNewsTitle());
                    }
                    mList.remove(position);
                    mAdapter.notifyItemRemoved(position);
//                    mList.clear();
//                    mList.addAll(mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).build().list());
//                    mRecyclerView.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();
                    setData();

                    break;
            }
        }
    };

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
            mParentLayout = mView.getParentLayout();
            mRecyclerView.setHasFixedSize(true);
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
                    public void onItemDissmiss(final int position) {

//                        Message message = new Message();
//                        message.what = Constants.STATE_ZERO;
//                        message.arg1 = position;
//                        mHandler.sendMessage(message);
                        Observable.create(new Observable.OnSubscribe<MyCollect>() {


                            @Override
                            public void call(Subscriber<? super MyCollect> subscriber) {

                                subscriber.onNext(mList.get(position));

                            }
                        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MyCollect>() {
                            @Override
                            public void call(MyCollect collect) {
                                if (!collect.getMeiZhiImageDesc().equals("")) {
                                    deleteMeiZhi(collect.getMeiZhiImageDesc());
                                } else if (!collect.getNewsTitle().toString().equals("")) {
                                    deleteNews(collect.getNewsTitle());
                                }
                                mList.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                setData();
//                                mAdapter.notifyDataSetChanged();
                            }
                        });


                    }
                };
                mCallback = new SimpleItemTouchHelperCallback(mHelper);
                ItemTouchHelper helper = new ItemTouchHelper(mCallback);
                helper.attachToRecyclerView(mRecyclerView);
            }
        }
    }

    //数据库删除
    public void deleteNews(String str) {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.NewsTitle.eq(str)).build().list();
        for (MyCollect item : list) {
            mDao.delete(item);
        }
    }

    public void deleteMeiZhi(String str) {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.UserName.eq(userName)).where(MyCollectDao.Properties.MeiZhiImageDesc.eq(str)).build().list();
        for (MyCollect item : list) {
            mDao.delete(item);
        }
    }
}
