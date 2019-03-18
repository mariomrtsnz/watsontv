package com.mario.watsontv.retrofit.services;

import com.mario.watsontv.responses.ResponseContainer;
import com.mario.watsontv.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserService {

    String BASE_URL = "/users";

    @GET(BASE_URL)
    Call<ResponseContainer<UserResponse>> listUsers();

    /**
     * Call that invokes the whole list of users, but with an additional field which contains
     * a boolean attribute about if every single user is a friend of us or not
     *
     * @return The list of users
     */

//    @GET(BASE_URL + "/friended")
//    Call<List<PeopleResponse>> listUsersAndFriended();
//
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

    // It should be /me, must do in api first
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
