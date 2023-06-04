package com.example.instagram.viewmodel;



import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.User;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.token.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> mutableUser;
    public ProfileViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }
    public void getProfile(){
//        Log.d("xxx", "getProfile:");
        Call<User> call = RetrofitService.getProfileAPI().get("Bearer " + Token.getToken());
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
    public MutableLiveData<User> getObservedUser(){
        return mutableUser;
    }
    }

