package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    String BASE_URL = "/users";

    @GET(BASE_URL)
    Call<ResponseContainer<UserResponse>> listUsers(@Query("allUsers") boolean allUsers, @Query("page") int page);

//    @GET(BASE_URL + "/{id}")
//    Call<MyProfileResponse> getUser(@Path("id") String id);

    @GET(BASE_URL + "/{id}")
    Call<UserResponse> getUserResponse(@Path("id") String id);

    @GET(BASE_URL + "/me")
    Call<UserResponse> getMe();

//    @PUT(BASE_URL + "/{id}")
//    Call<UserEditResponse> editUser(@Path("id") String id, @Body UserEditDto user);

    @PUT(BASE_URL + "/{id}/password")
    Call<UserResponse> editPassword(@Path("id") String id, @Body String password);

//    @DELETE("/users/{id}")
//    Call<User> deleteUser(@Path("id") Long id);
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
}
