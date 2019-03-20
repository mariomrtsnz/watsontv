package com.mario.watsontv.ui.auth.login;

import android.app.ProgressDialog;

public interface LoginListener {
    void onLoginSubmit(String credentials, ProgressDialog pgDialog);
    void goToSignup();
}
