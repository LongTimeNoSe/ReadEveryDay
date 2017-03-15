package com.readeveryday.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
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
import com.readeveryday.bean.zhihu.NewsTimeLine;
import com.readeveryday.bean.zhihu.Stories;
import com.readeveryday.bean.zhihu.TopStories;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class ZhiHuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private NewsTimeLine newsTimeLine;
    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;
    private static final int TYPE_TOP = -1;
    private static final int TYPE_FOOTER = -2;

    public ZhiHuAdapter(Context context, NewsTimeLine newsTimeLine) {
        this.context = context;
        this.newsTimeLine = newsTimeLine;
    }

    @Override
    public int getItemViewType(int position) {
        if (newsTimeLine.getTop_stories() != null) {
            if (position == 0) {
                return TYPE_TOP;
            } else if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return position;
            }
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return position;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_TOP) {
            View rootView = View.inflate(parent.getContext(), R.layout.adapter_zhihu_top, null);
            return new TopViewHolder(rootView);
        } else if (viewType == TYPE_FOOTER) {
            View rootView = View.inflate(parent.getContext(), R.layout.adapter_zhihu_footer, null);
            return new FooterViewHolder(rootView);
        } else {
            View rootView = View.inflate(parent.getContext(), R.layout.adapter_zhihu_content, null);
//            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_zhihu_content, parent, false);
            return new ContentViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof TopViewHolder) {
            TopViewHolder topViewHolder = (TopViewHolder) holder;
            topViewHolder.bindView(newsTimeLine.getTop_stories());
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.bindView(newsTimeLine.getStories().get(position - 1));
        }


    }

    @Override
    public int getItemCount() {
        return newsTimeLine.getStories().size() + 2;
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_top_stories)
        ViewPager mVpTopStories;
        @BindView(R.id.tv_top_title)
        TextView mTvTopTitle;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(List<TopStories> stories) {

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
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.instance(context).dip2px(40));
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

    class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_stories_img)
        ImageView mIvStoriesImg;
        @BindView(R.id.tv_stories_title)
        TextView mTvStoriesTitle;
        @BindView(R.id.cv_item)
        CardView mCvItem;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ScreenUtil screenUtil = ScreenUtil.instance(context);
            int screenWidth = screenUtil.getScreenWidth();
            mCvItem.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        public void bindView(Stories stories) {

            String[] images = stories.getImages();
            Glide.with(context).load(images[0]).centerCrop().into(mIvStoriesImg);
            mTvStoriesTitle.setText(stories.getTitle());
            mCvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptUtil.toastShowShort(context, "点击");
                }
            });

        }
    }

    // change recycler state
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }
}
