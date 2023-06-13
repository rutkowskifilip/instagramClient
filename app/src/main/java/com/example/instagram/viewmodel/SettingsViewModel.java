package com.example.instagram.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.Post;
import com.example.instagram.model.User;
import com.example.instagram.requests.UpdateProfileRequest;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.store.Store;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<User> mutableUser;

    public SettingsViewModel(){
        this.mutableUser = new MutableLiveData<>();

    }
    public void updateUser(String name, String lastname, String password, String profilePic){
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(Store.getUser().getEmail(), name, lastname, password, profilePic);
        Call<User> call = RetrofitService.getProfileAPI().update("Bearer " + Store.getToken(), updateProfileRequest);

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
}
