package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.responses.ResponseContainer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CollectionService {
    String BASE_URL = "/collections";

    @GET(BASE_URL)
    Call<ResponseContainer<CollectionResponse>> getOneCollection();

    @GET(BASE_URL + "/user/{id}")
    Call<List<CollectionResponse>> getUserCollections(@Path("id") String id);
}
