package com.example.instagram.api;

import com.example.instagram.model.User;
import com.example.instagram.requests.LoginRequest;
import com.example.instagram.requests.RegisterRequest;
import com.example.instagram.requests.UpdateProfileRequest;
import com.example.instagram.responses.LoginResponse;
import com.example.instagram.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("/api/users/register")
    Call<RegisterResponse> register(@Body RegisterRequest user);

    @POST("/api/users/login")
    Call<LoginResponse> login(@Body LoginRequest user);


}
