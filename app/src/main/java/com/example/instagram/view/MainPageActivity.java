package com.example.instagram.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.instagram.R;
import com.example.instagram.adapters.PostAdapter;
import com.example.instagram.databinding.ActivityMainPageBinding;
import com.example.instagram.token.Token;


import java.util.Arrays;
import java.util.List;

@UnstableApi public class MainPageActivity extends AppCompatActivity {
private ActivityMainPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        View decorView = getWindow().getDecorView();
// Hide the status bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(view);
        String imageUrl = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=460,height=600,fit=cover/animal/breed/pictures/613f5a373cb17614656987.jpg";
        String imageUrl2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        String imageUrl3 = "https://apetete.pl/blog/wp-content/uploads/2017/03/brytyjczyki-koty.png";
        String imageUrl4 = "https://hodowlakotybrytyjskie.pl/wordpress/wp-content/uploads/2023/01/IMG_71721-scaled.jpg";
        List<String> data = Arrays.asList(imageUrl2, imageUrl, imageUrl3, imageUrl4, imageUrl2,imageUrl3);
        RecyclerView listView = binding.listView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainPageActivity.this);
        listView.setLayoutManager(layoutManager);
        PostAdapter adapter = new PostAdapter(MainPageActivity.this, data);
        listView.setAdapter(adapter);
        binding.bottomNavigation.setSelectedItemId(R.id.mainpage);
        binding.bottomNavigation.setOnItemSelectedListener(v -> {
            Log.d("xxx", String.valueOf(v));
            if(String.valueOf(v).equals("profile")){
                Intent profile = new Intent(MainPageActivity.this, ProfileActivity.class);
                startActivity(profile);
            }
            return false;
        });

    }
}