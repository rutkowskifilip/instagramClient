package com.example.instagram.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.User;
import com.example.instagram.requests.LoginRequest;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.token.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<User> mutableUser;

    private LoginViewModelListener listener;


    public void setListener(LoginViewModelListener listener) {
        this.listener = listener;
    }

    public LoginViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void login(LoginRequest user){
        Call<User> call = RetrofitService.getUserAPI().login(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {


                    mutableUser.setValue(response.body());
                }else if(response.code() == 400){

                    listener.showAlert("Attention!", "Wrong password provided or user with this email doesn't exist.");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    public MutableLiveData<User> getObservedUser(){
        return mutableUser;
    }
}
