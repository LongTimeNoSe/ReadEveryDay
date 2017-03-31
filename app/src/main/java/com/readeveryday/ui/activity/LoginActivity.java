package com.readeveryday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.readeveryday.R;
import com.readeveryday.ui.base.BaseActivity;
import com.readeveryday.ui.presenter.LoginPresenter;
import com.readeveryday.ui.presenter.LoginPresenter.LoginInterface;
import com.readeveryday.ui.presenter.LoginPresenter.RegisterInterface;
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

    LoginInterface mLoginInterface = new LoginInterface() {
        @Override
        public void toLogin() {
//            startActivity(new Intent(mContext, MainActivity.class));
            LoginActivity.this.finish();
        }
    };
    RegisterInterface mRegisterInterface = new RegisterInterface() {
        @Override
        public void toRegister() {
            startActivity(new Intent(mContext, RegisterActivity.class));
        }
    };

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.login(mLoginInterface, mRegisterInterface, mSharedPreferences.edit());
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.transparentStatusBar(this);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == event.KEYCODE_BACK) {
//
//            startActivity(MainActivity.class);
//        }
//        return false;
//    }
}
