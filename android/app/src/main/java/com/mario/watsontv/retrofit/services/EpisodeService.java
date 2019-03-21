package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EpisodeService {
    String BASE_URL = "/episodes";

    @PUT(BASE_URL + "/updateWatched/{id}")
    Call<UserResponse> updateWatched(@Path("id") String id);

    @PUT(BASE_URL + "/updateWatchlisted/{id}")
    Call<UserResponse> updateWatchlisted(@Path("id") String id);
}
