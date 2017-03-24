package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import com.readeveryday.ui.activity.ZhiHuDetailActivity;
import com.readeveryday.utils.PromptUtil;
import com.readeveryday.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/15.
 */

public class ZhiHuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private NewsTimeLine newsTimeLine;
    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;
    private static final int TYPE_TOP = -1;
    private static final int TYPE_FOOTER = -2;

    // 执行周期性或定时任务
    private ScheduledExecutorService mScheduledExecutorService;

    public ZhiHuAdapter(Context context, NewsTimeLine newsTimeLine) {
        this.mContext = context;
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
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder topViewHolder = (TopViewHolder) holder;
            topViewHolder.startAutoRun();
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder topViewHolder = (TopViewHolder) holder;
            topViewHolder.stopAutoRun();
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
        private int currentItem = 0;// ImageViewpager当前页面的index
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mVpTopStories.setCurrentItem(currentItem);
            }
        };

        final List<ImageView> imageViewList = new ArrayList<ImageView>();

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final List<TopStories> stories) {

            for (int i = 0; i < stories.size(); i++) {
                ImageView imageView = new ImageView(mContext);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(layoutParams);
                Glide.with(mContext).load(stories.get(i).getImage()).centerCrop().into(imageView);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
                        intent.putExtra("newsId", stories.get(finalI).getId());
                        mContext.startActivity(intent);
                    }
                });
                imageViewList.add(imageView);
            }
            mVpTopStories.setAdapter(new ZhiHuViewPagerAdapter(imageViewList));
            mTvTopTitle.setText(stories.get(0).getTitle());
            mVpTopStories.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mTvTopTitle.setText(stories.get(position).getTitle());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

        /**
         * 开启定时任务
         */
        public void startAutoRun() {
            mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            /**循环
             * 创建并执行一个在给定初始延迟后首次启用的定期操作， 后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，
             * 然后在initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行， 依此类推
             */
            mScheduledExecutorService.scheduleAtFixedRate(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
        }

        /**
         * 关闭定时任务
         */
        public void stopAutoRun() {
            if (mScheduledExecutorService != null) {
                mScheduledExecutorService.shutdown();
            }
        }

        /**
         * 发消息改变页数
         *
         * @author sujingbo
         */
        class ViewPagerTask implements Runnable {

            @Override
            public void run() {
                if (imageViewList != null) {
                    currentItem = (currentItem + 1) % imageViewList.size();
                    handler.obtainMessage().sendToTarget();
                }
            }
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
            ScreenUtil screenUtil = ScreenUtil.instance(mContext);
            int screenWidth = screenUtil.getScreenWidth();
            mCvItem.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
//            int screenWidthTwo = context.getResources().getDisplayMetrics().widthPixels;
//            mCvItem.setLayoutParams(new LinearLayout.LayoutParams(screenWidthTwo, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        public void bindView(final Stories stories) {

            String[] images = stories.getImages();
            Glide.with(mContext).load(images[0]).centerCrop().into(mIvStoriesImg);
            mTvStoriesTitle.setText(stories.getTitle());
            mCvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);

                    intent.putExtra("newsId", stories.getId());

                    mContext.startActivity(intent);
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
