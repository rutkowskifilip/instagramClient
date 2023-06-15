package com.example.instagram.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instagram.model.Post;
import com.example.instagram.requests.FilterRequest;
import com.example.instagram.requests.TagRequest;
import com.example.instagram.service.RetrofitService;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostViewModel extends ViewModel {
    private MutableLiveData<Post> mutablePost;

    public UploadPostViewModel() {
        this.mutablePost = new MutableLiveData<>();
    }

    public void filter(FilterRequest filterRequest) {
        Call<Post> call = RetrofitService.getFiltersAPI().filter(filterRequest);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                mutablePost.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("xxx", t.getMessage());
            }
        });
    }

    public void sendPost(RequestBody album, MultipartBody.Part body) {
        Call<Post> call = RetrofitService.getPostAPI().sendPost(album, body);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                mutablePost.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("xxx", t.getMessage());
            }
        });
    }

    public void refresh() {
        mutablePost.setValue(mutablePost.getValue());
    }

    public void addTags(int id, ArrayList<String> tags) {

        TagRequest tagsReq = new TagRequest(id, tags);
        Call<Post> call = RetrofitService.getPostAPI().addTags(tagsReq);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                mutablePost.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, Throwable t) {
                Log.d("xxx", "error " + t.getMessage());
            }
        });
    }

    public MutableLiveData<Post> getObservedPost() {
        return mutablePost;
    }
}
