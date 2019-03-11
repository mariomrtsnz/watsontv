package com.mario.watsontv.ui.auth.signup;

import com.mario.watsontv.dto.RegisterDto;

public interface SignupListener {
    void onSignupSubmit(RegisterDto registerDto);
    void goToLogin();
}
