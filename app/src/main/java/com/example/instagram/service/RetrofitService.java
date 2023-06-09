package com.example.instagram.service;

import com.example.instagram.api.PostAPI;
import com.example.instagram.api.ProfileAPI;
import com.example.instagram.api.UserAPI;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static  String BASE_URL = "http://10.0.2.2:3000";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    public static UserAPI getUserAPI()
    {
        return retrofit.create(UserAPI.class);
    }
    public static ProfileAPI getProfileAPI()
    {
        return retrofit.create(ProfileAPI.class);
    }
    public static PostAPI getPostAPI()
    {
        return retrofit.create(PostAPI.class);
    }
}
