package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.readeveryday.R;
import com.readeveryday.bean.gank.Gank;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;
import com.readeveryday.ui.activity.MeiZhiDetailActivity;

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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void BindItem(final int position) {
            Glide.with(mContext).load(mList.get(position).getUrl()).centerCrop().error(R.drawable.loder_error).into(mIvBeauty);
            mIvBeauty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MeiZhiDetailActivity.class);
                    intent.putExtra("url", mList.get(position).getUrl());
                    intent.putExtra("imageDesc", mList.get(position).getDesc());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}