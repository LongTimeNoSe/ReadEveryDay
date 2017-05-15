package com.recyclerviewlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by XuYanping on 2017/5/15.
 */

public class SimpleRecyclerViewAdapter extends Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Object> mObjects;
    private int layoutId;
    private int footLayoutId;
    private int status = 1;
    private static final int TYPE_FOOTER = -1;

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == position + 1) {
            return TYPE_FOOTER;
        } else {
            return position;
        }
    }

    public SimpleRecyclerViewAdapter(Context context, List<Object> objects, int layoutId, int footLayoutId) {
        mContext = context;
        mObjects = objects;
        this.layoutId = layoutId;
        this.footLayoutId = footLayoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(footLayoutId, null);
            return new SimpleRecyclerViewViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            return new SimpleRecyclerViewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleRecyclerViewViewHolder simpleRecyclerViewViewHolder = (SimpleRecyclerViewViewHolder) holder;
        simpleRecyclerViewViewHolder.BindView(mObjects.get(position));
    }


    @Override
    public int getItemCount() {
        return mObjects.size() + 1;
    }
}
