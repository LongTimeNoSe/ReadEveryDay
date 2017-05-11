package com.readeveryday.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.LoginPresenter;
import com.readeveryday.ui.view.LoginView;
import com.readeveryday.utils.StatusBarUtil;

import butterknife.BindView;

import static com.readeveryday.MyApplication.mContext;

/**
 * Created by XuYanping on 2017/3/30.
 */

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_psw)
    EditText mEtPsw;
    @BindView(R.id.bt_register)
    Button mBtRegister;

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
        return "LoginActivity";
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
    public Button register() {
        return mBtRegister;
    }

    @Override
    public void finishActivity() {
        LoginActivity.this.finish();
    }

    @Override
    public void toRegister() {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }

    @Override
    public SharedPreferences.Editor getEdit() {
        return mSharedPreferences.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.login();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.transparentStatusBar(this);
    }

}
