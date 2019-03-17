package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.GenreResponse;
import com.mario.watsontv.responses.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GenreService {

    String BASE_URL = "/genres";

    @GET(BASE_URL)
    Call<ResponseContainer<GenreResponse>> getAllGenres(@Query("sort") String name);

    @GET(BASE_URL + "/{id}")
    Call<GenreResponse> getOneCategory(@Path("id") String id);
}
