package com.example.instagram.service;

import com.example.instagram.api.UserAPI;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String BASE_URL = "http://10.0.2.2:3000";

    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    public static UserAPI getUserAPI()
    {
        return retrofit.create(UserAPI.class);
    }
}
