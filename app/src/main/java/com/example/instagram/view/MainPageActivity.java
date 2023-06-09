package com.example.instagram.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        replaceFragment(new PostListFragment());

//        binding.bottomNavigation.setSelectedItemId(R.id.mainpage);
        binding.bottomNavigation.setOnItemSelectedListener(v -> {
            Log.d("xxx", String.valueOf(v));
            if(v.getItemId() == R.id.profilPage){
                replaceFragment(new ProfileFragment());
                binding.bottomNavigation.setSelected(false);
            }else if(v.getItemId() == R.id.mainpage){
                replaceFragment(new PostListFragment());
                binding.bottomNavigation.setSelected(false);
            }
            return true;
        });

    }
}