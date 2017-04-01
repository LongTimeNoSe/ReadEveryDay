package com.readeveryday.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.readeveryday.R;


public class ArcMenu extends ViewGroup implements OnClickListener {

    private Position mPosition = Position.RIGHT_BOTTOM;
    private int mRadius;// 半径

    private Status mCurrStatus = Status.CLOSE;// 当前状态

    private View mCButton;// 菜单的主按钮

    private onMenuItemClickListener mMenuItemClickListener;

    public static final int POS_LEFT_TOP = 0;
    public static final int POS_LEFT_BOTTOM = 1;
    public static final int POS_RIGHT_TOP = 2;
    public static final int POS_RIGHT_BOTTOM = 3;

    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 菜单位置的枚举类
     */
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    // 点击子菜单项的回调接口
    public interface onMenuItemClickListener {
        void onclick(View view, int position);
    }

    public void setOnMenuItemClickListener(onMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public ArcMenu(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        // 获取自定义属性的值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AreMenu, defStyle, 0);
        int pos = array.getInt(R.styleable.AreMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        mRadius = (int) array.getDimension(R.styleable.AreMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        System.out.println("postion=" + mPosition);
        System.out.println("Radius=" + mRadius);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutCButton();
            int count = getChildCount();
            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(View.GONE);
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                // 如果菜单位置在底部 左下，右下
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                // 右上，右下
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }
                child.layout(cl, ct, cl + cWidth, ct + cHeight);

            }

        }

    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);
        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + width);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        rotateCButton(v, 0f, 360f, 300);// v,本身 0f,开始 360f，结束 300，时间
        toggleMenu(300);
    }

    // 切换菜单
    private void toggleMenu(int duration) {
        // TODO Auto-generated method stub
        // 为menuitem添加平移动画和旋转动画
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);
            // end 0,0
            // start
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xflag = 1;
            int yflag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xflag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yflag = -1;
            }
            AnimationSet animationSet = new AnimationSet(true);
            Animation traAnimation = null;
            // to open
            if (mCurrStatus == Status.CLOSE) {
                traAnimation = new TranslateAnimation(xflag * cl - 6, 0, yflag * ct - 6, 0);
                childView.setClickable(true);
                childView.setFocusable(true);

            } else {
                // to close
                traAnimation = new TranslateAnimation(0, xflag * cl - 6, 0, yflag * ct - 6 );
                childView.setClickable(false);
                childView.setFocusable(false);
            }
            traAnimation.setFillAfter(true);
            traAnimation.setDuration(duration);

            traAnimation.setStartOffset((i * 100) / count);

            traAnimation.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    // TODO Auto-generated method stub
                    if (mCurrStatus == Status.CLOSE) {
                        childView.setVisibility(View.GONE);
                    }
                }
            });

            // 旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setFillAfter(true);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(traAnimation);
            childView.startAnimation(animationSet);

            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (mMenuItemClickListener != null)
                        mMenuItemClickListener.onclick(childView, pos);
                    menuItemAnim(pos - 1);
                    changeStatus();

                }
            });

        }
        // 切换菜单状态
        changeStatus();

    }

    // 添加menuItem的点击动画
    private void menuItemAnim(int pos) {
        // TODO Auto-generated method stub
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }

            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    private Animation scaleSmallAnim(int duration) {
        // TODO Auto-generated method stub
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    // 为当前点击的item设置变大和降低透明度的动画
    private Animation scaleBigAnim(int duration) {
        // TODO Auto-generated method stub
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    private void changeStatus() {
        // TODO Auto-generated method stub
        mCurrStatus = (mCurrStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    private void rotateCButton(View v, float start, float end, int duration) {
        // TODO Auto-generated method stub
        RotateAnimation animation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        v.startAnimation(animation);

    }

}
