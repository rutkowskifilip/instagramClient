package com.example.instagram.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.instagram.R;
import com.example.instagram.databinding.ActivityMainPageBinding;

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
        replaceFragment(new SelectPhotoFragment());
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 100);
//        binding.bottomNavigation.setSelectedItemId(R.id.mainpage);
        binding.bottomNavigation.setOnItemSelectedListener(v -> {
            Log.d("xxx", String.valueOf(v));
            if(v.getItemId() == R.id.profilPage){
                replaceFragment(new ProfileFragment());
                binding.bottomNavigation.setSelected(false);
            }else if(v.getItemId() == R.id.mainPage){
                replaceFragment(new PostListFragment());
                binding.bottomNavigation.setSelected(false);
            }else if(v.getItemId() == R.id.selectPhotoPage){
                replaceFragment(new SelectPhotoFragment());
                binding.bottomNavigation.setSelected(false);
            }
            return true;
        });

    }
    public void checkPermission(String permission, int requestCode) {
        // jeśli nie jest przyznane to zażądaj

        if (ContextCompat.checkSelfPermission(MainPageActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            Log.d("xxx", "checkPermission: true");

            ActivityCompat.requestPermissions(MainPageActivity.this,new String[]{permission}, requestCode);
        } else {
            Log.d("xxx", "checkPermission: false");
            Toast.makeText(MainPageActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
}