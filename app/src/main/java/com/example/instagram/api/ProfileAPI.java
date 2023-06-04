package com.example.instagram.api;

import com.example.instagram.model.User;
import com.example.instagram.requests.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProfileAPI {

    @GET("/api/profile")
    Call<User> get(@Header("Authorization") String authHeader);
}
