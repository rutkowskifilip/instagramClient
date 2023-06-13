package com.example.instagram.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.User;
import com.example.instagram.requests.LoginRequest;
import com.example.instagram.responses.LoginResponse;
import com.example.instagram.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> mutableUser;

    private LoginViewModelListener listener;


    public void setListener(LoginViewModelListener listener) {
        this.listener = listener;
    }

    public LoginViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void login(LoginRequest user){
        Call<LoginResponse> call = RetrofitService.getUserAPI().login(user);
        Log.d("xx", "login: ");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 200) {

                    Log.d("xxx", "onResponse: ");
                    mutableUser.setValue(response.body());
                }else if(response.code() == 400){

                    listener.showAlert("Attention!", "Wrong password provided or user with this email doesn't exist.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("xxx", "onFailure: "+t.getMessage());
            }
        });
    }
    public MutableLiveData<LoginResponse> getObservedUser(){
        return mutableUser;
    }
}
