package com.readeveryday.ui.view;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by XuYanping on 2017/3/30.
 */

public interface LoginView {

    EditText getUserName();

    EditText getPsw();

    Button login();

    Button register();

    void finishActivity();

    void toRegister();

    SharedPreferences.Editor getEdit();
}
