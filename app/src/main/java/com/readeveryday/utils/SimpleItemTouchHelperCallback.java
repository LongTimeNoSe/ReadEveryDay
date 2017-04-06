package com.readeveryday.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by XuYanping on 2017/4/5.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private MyItemTouchHelper mHelper;

    public SimpleItemTouchHelperCallback(MyItemTouchHelper helper) {
        mHelper = helper;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }
//
//    @Override
//    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//        mHelper.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        return true;
//    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mHelper.onItemDissmiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
