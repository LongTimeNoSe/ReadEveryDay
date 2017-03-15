package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.base.BasePresenter;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.tv_third)
    TextView mTvThird;
    @BindView(R.id.second)
    TextView mSecond;
    @BindView(R.id.tv_first)
    TextView mTvFirst;

    @Override
    protected void onStart() {
        super.onStart();
        startAnimation();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_welcome;
    }

    private void startAnimation() {

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        final Animation anim_1 = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        final Animation anim_2 = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        anim_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                PromptUtil.toastShowShort(WelcomeActivity.this, "开始");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                PromptUtil.toastShowShort(WelcomeActivity.this, "结束");

                anim_2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        anim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                                WelcomeActivity.this.finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        mTvThird.setVisibility(View.VISIBLE);
                        mTvThird.setAnimation(anim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mSecond.setVisibility(View.VISIBLE);
                mSecond.setAnimation(anim_2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                PromptUtil.toastShowShort(WelcomeActivity.this, "重复");
            }
        });
        mTvFirst.setVisibility(View.VISIBLE);
        mTvFirst.setAnimation(anim_1);

    }
}
