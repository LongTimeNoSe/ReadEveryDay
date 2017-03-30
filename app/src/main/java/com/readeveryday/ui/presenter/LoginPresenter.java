package com.readeveryday.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.readeveryday.bean.UserInfo;
import com.readeveryday.ui.activity.MainActivity;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.LoginView;
import com.readeveryday.utils.PromptUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by XuYanping on 2017/3/30.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private Context mContext;
    private EditText mUserName, mPsw;
    private Button mLogin;
    private LoginView mView;
    private String userName, psw;

    private BmobQuery<UserInfo> mQuery;

    public LoginPresenter(Context context) {
        mContext = context;
        mQuery = new BmobQuery<UserInfo>();
    }

    public void login() {

        mView = getView();
        if (mView != null) {
            mUserName = mView.getUserName();
            mPsw = mView.getPsw();
            mLogin = mView.login();
            mLogin.setOnClickListener(mLoginClickListener);
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

            mQuery.getObject(userName, new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo info, BmobException e) {

                    if (e == null) {

                        if (info.getPsw().equals(psw)) {
                            PromptUtil.toastShowShort(mContext, "登录成功");
                            mContext.startActivity(new Intent(mContext, MainActivity.class));
                        } else {
                            PromptUtil.toastShowShort(mContext, "用户名或密码错误");
                        }
                    } else {
                        PromptUtil.toastShowShort(mContext, "错误" + e.getMessage());
                    }
                }
            });

        }
    };
}
