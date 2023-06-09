package com.example.instagram.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.Post;
import com.example.instagram.model.User;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.token.Token;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Post>> mutablePosts;
    public PostListViewModel(){
        this.mutablePosts = new MutableLiveData<>();
    }

    public void getPosts(){
        Call<ArrayList<Post>> call = RetrofitService.getPostAPI().getAll();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                Log.d("xxx", String.valueOf(response));
                mutablePosts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.d("xxx", t.getMessage());
            }
        });
    }



    public MutableLiveData<ArrayList<Post>> getObservedPosts(){
        return mutablePosts;
    }
}
