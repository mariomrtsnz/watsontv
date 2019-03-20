package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.SeasonDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeasonService {
    String BASE_URL = "/seasons";

    @GET(BASE_URL + "/{id}")
    Call<SeasonDetailsResponse> getOne(@Path("id") String id);
}
