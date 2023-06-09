package com.example.instagram.api;

import com.example.instagram.model.Post;
import com.example.instagram.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PostAPI {

    @GET("/api/getfile/{username}")
    Call<ArrayList<Post>> get(@Path("username") String username);

    @GET("/api/photos")
    Call<ArrayList<Post>> getAll();
}
