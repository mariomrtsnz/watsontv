package com.mario.watsontv.ui.auth.login;

public interface LoginListener {
    void onLoginSubmit(String credentials);
    void goToSignup();
}
