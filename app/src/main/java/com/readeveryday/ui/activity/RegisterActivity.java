package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.RegisterPresenter;
import com.readeveryday.ui.view.RegisterView;

import butterknife.BindView;

/**
 * Created by XuYanping on 2017/3/29.
 */

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPresenter> implements RegisterView {
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_psw)
    EditText mEtPsw;
    @BindView(R.id.et_sure_psw)
    EditText mEtSurePsw;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    public String getActivityTag() {
        return "RegisterActivity";
    }

    @Override
    public EditText getUserName() {
        return mEtUserName;
    }

    @Override
    public EditText getPsw() {
        return mEtPsw;
    }

    @Override
    public EditText getSurePsw() {
        return mEtSurePsw;
    }

    @Override
    public Button getRegister() {
        return mBtRegister;
    }

    @Override
    public ImageView getBack() {
        return mIvBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.initView();
    }

//    @Override
//    protected void setStatusBar() {
//        StatusBarUtil.transparentStatusBar(this);
//    }
}
