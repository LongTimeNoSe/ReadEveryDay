package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.R;
import com.readeveryday.bean.gank.Gank;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.activity.MeiZhiDetailActivity;
import com.readeveryday.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/21.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Gank> mList;
    private MyCollectDao mDao;
    private MyCollect mMyCollect;

    public MeiZhiAdapter(Context context, List<Gank> list) {
        mContext = context;
        mList = list;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMyCollectDao();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_meizhi_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.BindItem(position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_beauty)
        ImageView mIvBeauty;
        @BindView(R.id.tv_position)
        TextView mTvPosition;
        @BindView(R.id.collection)
        FloatingActionButton mCollection;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void BindItem(final int position) {
            mTvPosition.setText(position + 1 + "/" + mList.size());
            Glide.with(mContext).load(mList.get(position).getUrl()).centerCrop().into(mIvBeauty);
            mIvBeauty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MeiZhiDetailActivity.class);
                    intent.putExtra("url", mList.get(position).getUrl());
                    intent.putExtra("imageDesc", mList.get(position).getDesc());
                    mContext.startActivity(intent);
                }
            });
            if (queryMeiZhi(mList.get(position).getDesc()) != null && queryMeiZhi(mList.get(position).getDesc()).size() > 0) {
                mCollection.setImageResource(R.drawable.collected);
                mList.get(position).setCollected(true);
            } else {
                mList.get(position).setCollected(false);
                mCollection.setImageResource(R.drawable.collection);
            }

            mCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.get(position).isCollected()) {
                        deleteMeiZhi(position);
                        mCollection.setImageResource(R.drawable.collection);
                        mList.get(position).setCollected(false);
                    } else {
                        insertMeiZhi(position);
                        mCollection.setImageResource(R.drawable.collected);
                        mList.get(position).setCollected(true);
                    }
                }
            });
        }
    }

    //数据库增加
    public void insertMeiZhi(int position) {
        mMyCollect = new MyCollect(mList.get(position).getUrl(), mList.get(position).getDesc(), "", "", "", Constants.FROMMEIZHI);
        mDao.insert(mMyCollect);
    }

    //数据库查询
    public List<MyCollect> queryMeiZhi(String desc) {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.MeiZhiImageDesc.eq(desc)).build().list();
        return list;
    }

    //数据库删除
    public void deleteMeiZhi(int position) {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.MeiZhiImageDesc.eq(mList.get(position).getDesc())).build().list();
        for (MyCollect item : list) {
            mDao.delete(item);
        }
    }

    //数据库更改
    public void updateMeiZhi(String imageDesc, String newImageDesc, String newImageUrl) {
        List<MyCollect> list = mDao.queryBuilder().where(MyCollectDao.Properties.MeiZhiImageDesc.eq(imageDesc)).build().list();
        for (MyCollect item : list) {
            item.setMeiZhiImageDesc(newImageDesc);
            item.setMeiZhiImageUrl(newImageUrl);
            mDao.update(item);
        }
    }
}