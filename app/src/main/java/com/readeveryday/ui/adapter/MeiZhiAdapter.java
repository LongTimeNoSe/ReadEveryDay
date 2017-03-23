package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.MyApplication;
import com.readeveryday.R;
import com.readeveryday.bean.gank.Gank;
import com.readeveryday.greendao.MeiZhiCollection;
import com.readeveryday.greendao.MeiZhiCollectionDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.activity.MeiZhiDetailActivity;
import com.readeveryday.utils.PromptUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/21.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Gank> mList;
    private MeiZhiCollectionDao mDao;
    private MeiZhiCollection mMeiZhiCollection;

    public MeiZhiAdapter(Context context, List<Gank> list) {
        mContext = context;
        mList = list;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMeiZhiCollectionDao();
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

            if (queryMeiZhi(position) != null && queryMeiZhi(position).size() > 0) {
                mCollection.setImageResource(R.drawable.collected);
                mList.get(position).setCollected(true);
            } else {
                mCollection.setImageResource(R.drawable.collection);
            }
            mCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.get(position).isCollected()) {
                        deleteMeiZhi(position);
                        queryMeiZhi(position);
                        mCollection.setImageResource(R.drawable.collection);
                        mList.get(position).setCollected(false);
                    } else {
                        insertMeiZhi(position);
                        queryMeiZhi(position);
                        mCollection.setImageResource(R.drawable.collected);
                        mList.get(position).setCollected(true);
                    }
                }
            });
        }
    }

    //数据库增加
    public void insertMeiZhi(int position) {
        mMeiZhiCollection = new MeiZhiCollection((long) position, mList.get(position).getUrl(), mList.get(position).getDesc());
        mDao.insertOrReplace(mMeiZhiCollection);
    }

    //数据库查询
    public List<MeiZhiCollection> queryMeiZhi(int position) {
        List<MeiZhiCollection> list = mDao.queryBuilder().where(MeiZhiCollectionDao.Properties.ImageDesc.eq(mList.get(position).getDesc())).build().list();
        Log.d("query", list.toString());
        return list;
    }

    //数据库删除
    public void deleteMeiZhi(int position) {
        List<MeiZhiCollection> list = mDao.queryBuilder().where(MeiZhiCollectionDao.Properties.ImageDesc.eq(mList.get(position).getDesc())).build().list();
        for (MeiZhiCollection item : list) {
            mDao.delete(item);
        }
    }

    //数据库更改
    public void updateMeiZhi(String imageDesc, String newImageDesc, String newImageUrl) {
        List<MeiZhiCollection> list = mDao.queryBuilder().where(MeiZhiCollectionDao.Properties.ImageDesc.eq(imageDesc)).build().list();
        for (MeiZhiCollection item : list) {
            item.setImageDesc(newImageDesc);
            item.setImageUrl(newImageUrl);
            mDao.update(item);
        }
    }
}