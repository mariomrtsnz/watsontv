package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.dto.RegisterDto;
import com.mario.watsontv.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);


    @POST("/users")
    Call<LoginResponse> doSignUp(@Body RegisterDto register);

}
