package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.tv_third)
    TextView mTvThird;
    @BindView(R.id.second)
    TextView mSecond;
    @BindView(R.id.tv_first)
    TextView mTvFirst;
    @BindView(R.id.go_home)
    FloatingActionButton mGoHome;

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
        return R.layout.activity_about_me;
    }

    private void startAnimation() {

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        final Animation anim_1 = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        final Animation anim_2 = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        anim_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                PromptUtil.toastShowShort(AboutMeActivity.this, "开始");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                PromptUtil.toastShowShort(AboutMeActivity.this, "结束");

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
                                mGoHome.setVisibility(View.VISIBLE);

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
//                PromptUtil.toastShowShort(AboutMeActivity.this, "重复");
            }
        });
        mTvFirst.setVisibility(View.VISIBLE);
        mTvFirst.setAnimation(anim_1);

    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
                AboutMeActivity.this.finish();
            }
        });
    }
}
