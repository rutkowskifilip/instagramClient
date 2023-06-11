package com.example.instagram.api;

import com.example.instagram.model.User;
import com.example.instagram.requests.LoginRequest;
import com.example.instagram.requests.UpdateProfileRequest;
import com.example.instagram.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ProfileAPI {

    @GET("/api/profile")
    Call<User> get(@Header("Authorization") String authHeader);

    @PATCH("/api/profile")
    Call<User> update(@Header("Authorization") String authHeader, @Body UpdateProfileRequest updateProfileRequest);

}
