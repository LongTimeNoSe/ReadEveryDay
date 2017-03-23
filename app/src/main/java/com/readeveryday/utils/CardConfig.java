package com.readeveryday.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by XuYanping on 2017/3/20.
 * <p>
 * 介绍：一些配置
 * 界面最多显示几个View
 * 每一级View之间的Scale差异、translationY等等
 * <p>
 * 作者：zhangxutong
 */

public class CardConfig {

    public static int MAX_SHOW_COUNT;

    //每一级Scale相差0.05f，translationY相差7dp左右
    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 4;
        SCALE_GAP = 0.05f;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
    }

    /**
     * 显示可见的卡片数量
     */
    public static final int DEFAULT_SHOW_ITEM = 3;
    /**
     * 默认缩放的比例
     */
    public static final float DEFAULT_SCALE = 0.1f;
    /**
     * 卡片Y轴偏移量时按照14等分计算
     */
    public static final int DEFAULT_TRANSLATE_Y = 14;
    /**
     * 卡片滑动时默认倾斜的角度
     */
    public static final float DEFAULT_ROTATE_DEGREE = 15f;
    /**
     * 卡片滑动时不偏左也不偏右
     */
    public static final int SWIPING_NONE = 1;
    /**
     * 卡片向左滑动时
     */
    public static final int SWIPING_LEFT = 1 << 2;
    /**
     * 卡片向右滑动时
     * <p>
     * 1-00000001
     * << 3 左移3位 —000000100
     * 结果为4
     */
    public static final int SWIPING_RIGHT = 1 << 3;
    /**
     * 卡片从左边滑出
     */
    public static final int SWIPED_LEFT = 1;
    /**
     * 卡片从右边滑出
     */
    public static final int SWIPED_RIGHT = 1 << 2;

}
