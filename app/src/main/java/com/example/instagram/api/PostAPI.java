package com.example.instagram.api;

import android.location.LocationRequest;

import com.example.instagram.model.Post;
import com.example.instagram.model.User;
import com.example.instagram.requests.MapRequest;
import com.example.instagram.requests.TagRequest;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostAPI {

    @GET("/api/getfile/{username}")
    Call<ArrayList<Post>> get(@Path("username") String username);

    @GET("/api/photos")
    Call<ArrayList<Post>> getAll();

    @Multipart
    @POST("/api/photos")
    Call<Post> sendPost(@Part("directory") RequestBody album,
                        @Part MultipartBody.Part file);

    @PATCH("/api/photos/tags")
    Call<Post> addTags(@Body TagRequest tags);

    @PATCH("/api/photos/location")
    Call<Post> addLocation(@Body MapRequest location);
}
