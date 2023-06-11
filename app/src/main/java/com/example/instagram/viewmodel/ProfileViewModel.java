package com.example.instagram.viewmodel;



import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.Post;
import com.example.instagram.model.User;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.store.Store;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> mutableUser;
    private MutableLiveData<ArrayList<Post>> mutablePosts;
    public ProfileViewModel(){
        this.mutableUser = new MutableLiveData<>();
        this.mutablePosts = new MutableLiveData<>();
    }
    public void getProfile(){
//        Log.d("xxx", "getProfile:");
        Call<User> call = RetrofitService.getProfileAPI().get("Bearer " + Store.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mutableUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("xxx", t.getMessage());
            }
        });
        }
        public void getProfilePhotos(String username){
        Call<ArrayList<Post>> call = RetrofitService.getPostAPI().get(username);
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

    public MutableLiveData<User> getObservedUser(){
        return mutableUser;
    }

    public MutableLiveData<ArrayList<Post>> getObservedPosts(){
        return mutablePosts;
    }
}

