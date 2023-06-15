package com.example.instagram.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.Post;
import com.example.instagram.requests.MapRequest;
import com.example.instagram.service.RetrofitService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsViewModel extends ViewModel {
    private MutableLiveData<Post> mutablePost;

    public MapsViewModel(){
        this.mutablePost = new MutableLiveData<>();
    }
    public void addLocation(int id, String location){
        MapRequest mapRequest = new MapRequest(id, location);
        Call<Post> call = RetrofitService.getPostAPI().addLocation(mapRequest);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                mutablePost.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("xxx",t.getMessage());
            }
        });
    }
    public void refresh(){
        mutablePost.setValue(mutablePost.getValue());
    }

    public MutableLiveData<Post> getObservedPost() {
        return mutablePost;
    }
}
