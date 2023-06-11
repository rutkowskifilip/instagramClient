package com.example.instagram.api;

import com.example.instagram.model.Post;
import com.example.instagram.model.Tag;
import com.example.instagram.requests.TagRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;

public interface TagAPI {
    @GET("/api/tags")
    Call<List<Tag>> getTags();

    @PATCH("api/photos/tags/mass")
    Call<Post> setTags(@Body TagRequest requestBody);

}
