package com.example.instagram.api;

import com.example.instagram.model.Post;
import com.example.instagram.requests.FilterRequest;
import com.example.instagram.requests.TagRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

public interface FiltersAPI {
    @PATCH("/api/filters")
    Call<Post> filter(@Body FilterRequest filterRequest);
}
