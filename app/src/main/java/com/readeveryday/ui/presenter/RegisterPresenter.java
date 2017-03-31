package com.readeveryday.ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.readeveryday.bean.UserInfo;
import com.readeveryday.ui.base.BasePresenter;
import com.readeveryday.ui.view.RegisterView;
import com.readeveryday.utils.PromptUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by XuYanping on 2017/3/29.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private Context mContext;
    private RegisterView mView;
    private EditText mUserName, mPsw, mSurePsw;
    private Button mRegister;
    private ImageView mBack;
    private String userName, psw, surePsw;
    private UserInfo mUserInfo;

    public RegisterPresenter(Context context) {
        mContext = context;
        mUserInfo = new UserInfo();
    }

    public void initView() {
        mView = getView();
        if (mView != null) {
            mUserName = mView.getUserName();
            mPsw = mView.getPsw();
            mSurePsw = mView.getSurePsw();
            mRegister = mView.getRegister();
            mBack = mView.getBack();
            mRegister.setOnClickListener(mRegisterClickListener);
            mBack.setOnClickListener(mBackClickListener);
        }
    }

    View.OnClickListener mBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener mRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            userName = mUserName.getText().toString().trim();
            psw = mPsw.getText().toString().trim();
            surePsw = mSurePsw.getText().toString().trim();
            if (TextUtils.isEmpty(userName) | TextUtils.isEmpty(psw) | TextUtils.isEmpty(surePsw)) {
                PromptUtil.toastShowShort(mContext, "请先完善信息");
                return;
            }
            if (!psw.equals(surePsw)) {
                PromptUtil.toastShowShort(mContext, "两次输入密码不一致，请核对后继续");
                return;
            }
            mUserInfo.setUsername(userName);
            mUserInfo.setPassword(psw);
            mUserInfo.signUp(new SaveListener<UserInfo>() {

                @Override
                public void done(UserInfo info, BmobException e) {
                    if (e == null) {
                        PromptUtil.toastShowShort(mContext, "注册成功" + info.toString());
                    } else {
                        PromptUtil.toastShowShort(mContext, "注册失败" + e.getMessage());
                    }
                }
            });
//            mUserInfo.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e == null) {
//                        PromptUtil.toastShowShort(mContext, "注册成功");
//                    } else {
//                        PromptUtil.toastShowShort(mContext, "注册失败");
//                        Log.d("error", e.getMessage());
//                    }
//
//                }
//            });
        }
    };

    interface BackInterface {
        void goBack();
    }
}
