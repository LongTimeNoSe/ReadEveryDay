package com.readeveryday.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
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
import com.readeveryday.utils.ScreenUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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
    public int getItemCount() {
        return newsTimeLine.getStories().size() + 2;
    }

    class TopViewHolder extends RecyclerView.ViewHolder implements OnBannerListener {

        @BindView(R.id.vp_top_stories)
        Banner mVpTopStories;

        List<String> newsId = new ArrayList<>();
        List<String> imageList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();


        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final List<TopStories> stories) {

            newsId.clear();
            imageList.clear();
            titleList.clear();

            for (int i = 0; i < stories.size(); i++) {
                newsId.add(stories.get(i).getId());
                imageList.add(stories.get(i).getImage());
                titleList.add(stories.get(i).getTitle());
            }

            //设置banner样式
            mVpTopStories.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            //设置图片加载器
            mVpTopStories.setImageLoader(new GlideImageLoder());
            //设置图片集合
            mVpTopStories.setImages(imageList);
            //设置banner动画效果
            mVpTopStories.setBannerAnimation(Transformer.ZoomOut);
            //设置标题集合（当banner样式有显示title时）
            mVpTopStories.setBannerTitles(titleList);
            //设置自动轮播，默认为true
            mVpTopStories.isAutoPlay(true);
            //设置轮播时间
            mVpTopStories.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            mVpTopStories.setIndicatorGravity(BannerConfig.CENTER);
            //设置点击事件
            mVpTopStories.setOnBannerListener(this);
            //banner设置方法全部调用完毕时最后调用
            mVpTopStories.start();
        }

        @Override
        public void OnBannerClick(int position) {
            Intent intent = new Intent(mContext, ZhiHuDetailActivity.class);
            intent.putExtra("newsId", newsId.get(position));
            intent.putExtra("title", titleList.get(position));
            intent.putExtra("newsImageUrl", imageList.get(position));
            mContext.startActivity(intent);
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
                    intent.putExtra("title", stories.getTitle());
                    intent.putExtra("newsImageUrl", stories.getImages()[0]);
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

    class GlideImageLoder extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);
        }
    }
}
