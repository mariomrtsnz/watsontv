package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.dto.ProfileEditDto;
import com.mario.watsontv.responses.MediaResponse;
import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.responses.UserResponse;
import com.mario.watsontv.responses.UserTimeStats;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    String BASE_URL = "/users";

    @GET(BASE_URL)
    Call<ResponseContainer<UserResponse>> listUsers(@Query("name") String name, @Query("page") int page);

    @GET(BASE_URL + "/befriended")
    Call<List<UserResponse>> listFriends(@Query("name") String name, @Query("page") int page);

    @GET(BASE_URL + "/{id}")
    Call<UserResponse> getUser(@Path("id") String id);

    @GET(BASE_URL + "/me")
    Call<UserResponse> getMe();

    @PUT(BASE_URL + "/{id}")
    Call<UserResponse> editUser(@Path("id") String id, @Body ProfileEditDto user);

    @PUT(BASE_URL + "/{id}/password")
    Call<UserResponse> editPassword(@Path("id") String id, @Body String password);

//    @Multipart
//    @POST(BASE_URL + "/uploadProfilePicture")
//    Call<MyProfileResponse> uploadPictureProfile(@Part MultipartBody.Part avatar,
//                                                 @Part("id") RequestBody id);

    @PUT(BASE_URL + "/updateWatched/{id}")
    Call<UserResponse> updateWatched(@Path("id") String id);

    @PUT(BASE_URL + "/updateWatchlisted/{id}")
    Call<UserResponse> updateWatchlisted(@Path("id") String id);

    @PUT(BASE_URL + "/updateFriended/{id}")
    Call<UserResponse> updateFriended(@Path("id") String id);

    @GET(BASE_URL + "/myWatchlist")
    Call<List<MediaResponse>> getUserWatchlist();

    @GET(BASE_URL + "/{id}/stats")
    Call<Map<String, Float>> getUserStats(@Path("id") String id);

    @GET(BASE_URL + "/{id}/timeStats")
    Call<UserTimeStats> getUserTimeStats(@Path("id") String id);

    @GET(BASE_URL + "/{id}/dashboard")
    Call<UserTimeStats> getUserDashboardMedia(@Path("id") String id);
}
