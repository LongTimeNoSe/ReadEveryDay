package com.readeveryday.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readeveryday.Constants;
import com.readeveryday.R;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.greendao.MyCollectDao;
import com.readeveryday.manager.GreenDaoManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private MyCollectDao mDao;
    private static int TYPE_MEIZHI = 1;
    private static int TYPE_NEWS = 2;
    private static int TYPE_NODATA = -1;

    public CollectAdapter(Context context) {
        mContext = context;
        mDao = GreenDaoManager.getGreenDaoManager(mContext).getDaoSession().getMyCollectDao();
    }

    @Override
    public int getItemViewType(int position) {
        if (queryCollect() == null | queryCollect().size() <= 0) {
            return TYPE_NODATA;
        } else {
            if (queryCollect().get(position).getType().equals(Constants.FROMANDROID) | queryCollect().get(position).getType().equals(Constants.FROMZHIHU)) {
                return TYPE_NEWS;
            } else {
                return TYPE_MEIZHI;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MEIZHI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_meizhi, null);
            return new MeiZhiViewHolder(view);
        } else if (viewType == TYPE_NEWS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_news, null);
            return new NewsViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MeiZhiViewHolder) {
            MeiZhiViewHolder meiZhiViewHolder = (MeiZhiViewHolder) holder;
            meiZhiViewHolder.BindItem();
        } else if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.BindItem();
        }

    }

    @Override
    public int getItemCount() {
        return queryCollect().size();
    }

    public List<MyCollect> queryCollect() {
        List<MyCollect> list = mDao.queryBuilder().build().list();
        return list;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_news_title)
        TextView mTvNewsTitle;
        @BindView(R.id.iv_news_image)
        ImageView mIvNewsImage;
        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.card_collect_news)
        CardView mCardCollectNews;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void BindItem() {

        }
    }

    class MeiZhiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_meizhi)
        ImageView mIvMeizhi;
        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.card_collect_meizhi)
        CardView mCardCollectMeizhi;

        public MeiZhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void BindItem() {

        }
    }
}
