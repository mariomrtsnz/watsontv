package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.dto.CollectionDto;
import com.mario.watsontv.responses.CollectionResponse;
import com.mario.watsontv.responses.MediaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CollectionService {
    String BASE_URL = "/collections";

    @GET(BASE_URL + "/{id}")
    Call<CollectionResponse> getOneCollection(@Path("id") String id);

    @GET(BASE_URL + "/user/{id}")
    Call<List<CollectionResponse>> getUserCollections(@Path("id") String id);

    @POST(BASE_URL)
    Call<CollectionResponse> create(@Body CollectionDto collectionDto);

    @DELETE(BASE_URL + "/{id}")
    Call<CollectionResponse> delete(@Path("id") String id);

    @PUT(BASE_URL + "/{id}")
    Call<CollectionResponse> edit(@Path("id") String id);

    @PUT(BASE_URL + "/add/{id}")
    Call<CollectionResponse> addToCollections(@Body List<String> collectionsId, @Path("id") String mediaId);

    @GET(BASE_URL + "/{id}/media")
    Call<List<MediaResponse>> getCollectionMedia(@Path("id") String selectedCollectionId);
}
