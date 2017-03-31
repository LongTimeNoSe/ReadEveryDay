package com.readeveryday.ui.view;

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

}
