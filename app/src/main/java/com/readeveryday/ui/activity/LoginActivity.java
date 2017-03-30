package com.readeveryday.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.LoginPresenter;
import com.readeveryday.ui.view.LoginView;

import butterknife.BindView;

/**
 * Created by XuYanping on 2017/3/30.
 */

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_psw)
    EditText mEtPsw;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public String getActivityTag() {
        return null;
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
    public Button login() {
        return mBtLogin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.login();
    }
}
