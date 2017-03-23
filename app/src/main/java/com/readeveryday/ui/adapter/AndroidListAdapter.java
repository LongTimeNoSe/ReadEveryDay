package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.R;
import com.readeveryday.bean.Android.AndroidList.ResultsBean;
import com.readeveryday.ui.activity.AndroidDetailActivity;
import com.readeveryday.utils.ScreenUtil;
import com.readeveryday.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/17.
 */

public class AndroidListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<ResultsBean> mResultsBean;

    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;
    private static final int TYPE_FOOTER = -1;

    public AndroidListAdapter(Context context, List<ResultsBean> resultsBean) {
        mContext = context;
        mResultsBean = resultsBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_zhihu_footer, null);
            return new FooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_android_list, null);
            return new AndroidListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof AndroidListViewHolder) {
            AndroidListViewHolder androidListViewHolder = (AndroidListViewHolder) holder;
            androidListViewHolder.bindItem(mResultsBean.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (getItemCount() == position + 1) {
            return TYPE_FOOTER;
        } else {
            return position;
        }
    }

    @Override
    public int getItemCount() {
        return mResultsBean.size() + 1;
    }

    class AndroidListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_author)
        TextView mTvAuthor;
        @BindView(R.id.tv_time)
        TextView mTvTime;

        public AndroidListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(mContext.getResources().getDisplayMetrics().widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(params);
        }

        public void bindItem(final ResultsBean resultsBean) {

            if (resultsBean.getImages() != null) {
//                Glide.with(mContext).load(resultsBean.getImages().get(0)).centerCrop().into(mIvImage);
                //有部分图片为GIF图 显示Gif会消耗很大的内存 此处讲GIF修改未Image
                Glide.with(mContext).load(resultsBean.getImages().get(0)).asBitmap().centerCrop().into(mIvImage);
            } else {
                mIvImage.setVisibility(View.GONE);
            }
            mTvTitle.setText(resultsBean.getDesc());
            if (resultsBean.getWho() != null) {
                mTvAuthor.setText(resultsBean.getWho());
            } else {
                mTvAuthor.setText("匿名");
            }
            mTvTime.setText(TimeUtil.getTranslateTime(resultsBean.getCreatedAt()));
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AndroidDetailActivity.class);
                    intent.putExtra("url", resultsBean.getUrl());
                    intent.putExtra("title", resultsBean.getDesc());
                    mContext.startActivity(intent);
                }
            });

        }
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress)
        ProgressBar mProgress;
        @BindView(R.id.tv_load_prompt)
        TextView mTvLoadPrompt;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.instance(mContext).dip2px(40));
            itemView.setLayoutParams(params);
        }

        private void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    mProgress.setVisibility(View.VISIBLE);
                    mTvLoadPrompt.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    mProgress.setVisibility(View.GONE);
                    mTvLoadPrompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    System.out.println("LOAD_NONE----");
                    mProgress.setVisibility(View.GONE);
                    mTvLoadPrompt.setText("已无更多加载");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    }

    // change recycler state
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }
}
