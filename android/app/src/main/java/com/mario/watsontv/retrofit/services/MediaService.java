package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.MediaDetailsResponse;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MediaService {
    String BASE_URL = "/media";
    String SERIES_URL = "/series";
    String MOVIES_URL = "/movies";

    @GET(BASE_URL)
    Call<ResponseContainer<MediaResponse>> getAllMedia(@Query("genre") String genre);

    @GET(SERIES_URL + "/user")
    Call<ResponseContainer<MediaResponse>> getAllSeries(@Query("genre") String genre, @Query("page") int page);

    @GET(MOVIES_URL + "/user")
    Call<ResponseContainer<MediaResponse>> getAllMovies(@Query("genre") String genre, @Query("page") int page);

    @GET(BASE_URL + "/{id}")
    Call<MediaDetailsResponse> getOneMedia(@Path("id") String id);
}
