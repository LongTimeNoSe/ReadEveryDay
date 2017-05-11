package com.readeveryday.ui.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.readeveryday.bean.UserInfo;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.LoginView;
import com.readeveryday.utils.PromptUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by XuYanping on 2017/3/30.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private Context mContext;
    private EditText mUserName, mPsw;
    private Button mLogin, mRegister;
    private LoginView mView;
    private String userName, psw;
    private UserInfo mUserInfo;
    private BmobQuery<UserInfo> mQuery;

    private SharedPreferences.Editor mEditor;

    public LoginPresenter(Context context) {
        mContext = context;
        mUserInfo = new UserInfo();
        mQuery = new BmobQuery<UserInfo>();
    }

    public void login() {
        mView = getView();
        if (mView != null) {
            mUserName = mView.getUserName();
            mPsw = mView.getPsw();
            mLogin = mView.login();
            mRegister = mView.register();
            mEditor = mView.getEdit();
            mLogin.setOnClickListener(mLoginClickListener);
            mRegister.setOnClickListener(mRegisterClickListener);
        }

    }

    View.OnClickListener mLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            userName = mUserName.getText().toString().trim();
            psw = mPsw.getText().toString().trim();
            if (TextUtils.isEmpty(userName) | TextUtils.isEmpty(psw)) {
                PromptUtil.toastShowShort(mContext, "请先输入账号或密码");
                return;
            }
            BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(userName);
            bmobUser.setPassword(psw);
            bmobUser.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser user, BmobException e) {

                    if (e == null) {
                        mEditor.putString("userName", userName);
                        mEditor.putString("psw", userName);
                        mEditor.putBoolean("isLoging", true);
                        mEditor.apply();
                        mView.finishActivity();
                    } else {
                        PromptUtil.toastShowShort(mContext, "账号或密码错误" + e.getMessage());
                    }
                }
            });
        }
    };

    View.OnClickListener mRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mView.toRegister();
            mView.finishActivity();
        }
    };
}
