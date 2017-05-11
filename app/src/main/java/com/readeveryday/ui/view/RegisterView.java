package com.readeveryday.ui.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by XuYanping on 2017/3/29.
 */

public interface RegisterView {

    EditText getUserName();

    EditText getPsw();

    EditText getSurePsw();

    Button getRegister();

    ImageView getBack();

    void finishActivity();

    void returnLogin();
}
