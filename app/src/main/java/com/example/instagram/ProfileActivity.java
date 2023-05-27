package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.instagram.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {
private ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("username");

        binding.bottomNavigation.setSelectedItemId(R.id.profilPage);
        binding.bottomNavigation.setOnItemSelectedListener(v -> {
            Log.d("xxx", String.valueOf(v));
            if(String.valueOf(v).equals("home")){
                Intent profile = new Intent(ProfileActivity.this, MainPageActivity.class);
                startActivity(profile);
            }
            return false;
        });
        String imageUrl = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=460,height=600,fit=cover/animal/breed/pictures/613f5a373cb17614656987.jpg";

        ArrayList images = new ArrayList<String>(Arrays.asList(
                imageUrl, imageUrl, imageUrl,imageUrl, imageUrl, imageUrl,imageUrl, imageUrl, imageUrl
                ));
//        ArrayList images = new ArrayList<>(Arrays.asList(
//                R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,  R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,  R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img
//        ));
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
        binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ProfilePostAdapter adapter = new ProfilePostAdapter(images);
        binding.recyclerView.setAdapter(adapter);

    }
}