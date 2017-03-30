package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by XuYanping on 2017/3/29.
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView mIvWelcome;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    public String getActivityTag() {
        return "WelcomeActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAnimation();
    }

    private void startAnimation() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.welcom_alpha_anim);
        final Animation animEnd = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha_anim_end);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                animEnd.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mIvWelcome.setVisibility(View.GONE);
                        startActivity(MainActivity.class);
//                        startActivity(RegisterActivity.class);
                        WelcomeActivity.this.finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mIvWelcome.setAnimation(animEnd);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mIvWelcome.setAnimation(anim);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.transparentStatusBar(this);
    }
}
