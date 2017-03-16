package com.readeveryday.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.readeveryday.R;
import com.readeveryday.bean.gank.Gank;
import com.readeveryday.utils.PromptUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XuYanping on 2017/3/16.
 */

public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Gank> mList;

    public GankAdapter(Context context, List<Gank> list) {
        mContext = context;
        mList = list;
    }

    public GankAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_meizi, null);

        return new GankMeiZhiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GankMeiZhiViewHolder) {
            GankMeiZhiViewHolder meiZhiViewHolder = (GankMeiZhiViewHolder) holder;
            meiZhiViewHolder.bindItem(mList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GankMeiZhiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_meizhi)
        ImageView mIvMeizhi;
        @BindView(R.id.tv_meizhi_title)
        TextView mTvMeizhiTitle;
        @BindView(R.id.card_meizhi)
        CardView mCardMeizhi;

        public GankMeiZhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Gank meizhi) {

            mTvMeizhiTitle.setText(meizhi.getDesc());
            Glide.with(mContext).load(meizhi.getUrl()).centerCrop().into(mIvMeizhi);
            mIvMeizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptUtil.toastShowShort(mContext, "图片点击");
                }
            });
            mCardMeizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptUtil.toastShowShort(mContext, "条目点击");
                }
            });
        }
    }
}
