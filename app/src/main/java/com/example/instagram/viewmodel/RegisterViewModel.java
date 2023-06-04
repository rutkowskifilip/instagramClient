package com.example.instagram.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.requests.RegisterRequest;
import com.example.instagram.responses.RegisterResponse;
import com.example.instagram.service.RetrofitService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<String> mutableUser;

    private RegisterViewModelListener listener;
    private MutableLiveData<String> linkText = new MutableLiveData<>();

    public LiveData<String> getLinkText() {
        return linkText;
    }
    public void setLinkText(String text) {
        linkText.setValue(text);
    }

    public void onLinkClicked() {
        try{
            Thread.sleep(3000);
            mutableUser.setValue("confirmed");
        }catch (InterruptedException  e){

        }

    }
    public void setListener(RegisterViewModelListener listener) {
        this.listener = listener;
    }
    public RegisterViewModel(){
        this.mutableUser = new MutableLiveData<>();
    }

    public void register(RegisterRequest user){
        Call<RegisterResponse> call = RetrofitService.getUserAPI().register(user);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.code() == 201) {
                    Log.d("xxx", response.body().getMessage());
                    setLinkText(response.body().getMessage());
                    listener.showAlert("Attention!");

                }else if (response.code() == 400){
                    setLinkText("User with this email already exists");
                    listener.showAlert("Attention!");

                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("xxx", t.getMessage());
            }
        });
    }
    public MutableLiveData<String> getObservedUser(){
        return mutableUser;
    }

}
