package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import com.example.instagram.databinding.ActivityMainPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Arrays;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
private ActivityMainPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        List<String> data = Arrays.asList("wynik 1", "wynik 2", "wynik 3", "wynik 1", "wynik 2", "wynik 3");
        ListView listView = binding.listView;
        PostAdapter adapter = new PostAdapter(this, data);
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