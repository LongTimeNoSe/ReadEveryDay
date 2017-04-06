package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.Constants;
import com.readeveryday.R;
import com.readeveryday.greendao.MyCollect;
import com.readeveryday.ui.activity.AndroidDetailActivity;
import com.readeveryday.ui.activity.MeiZhiDetailActivity;
import com.readeveryday.ui.activity.ZhiHuDetailActivity;
import com.readeveryday.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/24.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<MyCollect> mList;
    private ScreenUtil screenUtil;
    private int screenWidth;

    public CollectAdapter(Context context, List<MyCollect> list) {
        mContext = context;
        mList = list;
        screenUtil = ScreenUtil.instance(context);
        screenWidth = screenUtil.getScreenWidth();
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (type.equals(Constants.FROMMEIZHI)) {
            return Constants.TYPE_MEIZHI;
        } else {
            return Constants.TYPE_NEWS;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_MEIZHI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_meizhi, parent, false);
            return new MeiZhiViewHolder(view);
        } else if (viewType == Constants.TYPE_NEWS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_collect_news, parent, false);
            return new NewsViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyCollect myCollect = mList.get(position);

        if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.BindItem(myCollect.getNewsTitle(), myCollect.getNewsImageUrl(), myCollect.getNewsUrl(), mList.get(position).getNewsId(), mList.get(position).getType());
        } else if (holder instanceof MeiZhiViewHolder) {
            MeiZhiViewHolder meiZhiViewHolder = (MeiZhiViewHolder) holder;
            meiZhiViewHolder.BindItem(mList.get(position).getMeiZhiImageUrl(), mList.get(position).getMeiZhiImageDesc());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
            mCardCollectNews.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        public void BindItem(final String title, final String newsImageUrl, final String newsUrl, final String newsId, String type) {
            mTvNewsTitle.setText(title);
            Glide.with(mContext).load(newsImageUrl).centerCrop().error(R.drawable.loder_error).into(mIvNewsImage);
            boolean fromZhiHu = false;
            switch (type) {
                case Constants.FROMANDROID:
                    mTvFrom.setText("来自" + Constants.FROMANDROID);
                    fromZhiHu = false;
                    break;
                case Constants.FROMZHIHU:
                    mTvFrom.setText("来自" + Constants.FROMZHIHU);
                    fromZhiHu = true;
                    break;
            }
            final boolean finalFromZhiHu = fromZhiHu;
            mCardCollectNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (finalFromZhiHu) {
                        intent = new Intent(mContext, ZhiHuDetailActivity.class);
                        intent.putExtra("newsId", newsId);
                        intent.putExtra("newsImageUrl", newsImageUrl);
                        intent.putExtra("title", title);

                    } else {
                        intent = new Intent(mContext, AndroidDetailActivity.class);
                        intent.putExtra("url", newsUrl);
                        intent.putExtra("title", title);
                    }
                    mContext.startActivity(intent);
                }
            });

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
            mCardCollectMeizhi.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        public void BindItem(final String imageUrl, final String imageDesc) {
            mTvFrom.setText("来自" + Constants.FROMMEIZHI);
            Log.d("url", imageUrl);
            Glide.with(mContext).load(imageUrl).centerCrop().error(R.drawable.loder_error).into(mIvMeizhi);
            mCardCollectMeizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MeiZhiDetailActivity.class);
                    intent.putExtra("url", imageUrl);
                    intent.putExtra("imageDesc", imageDesc);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
