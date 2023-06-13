package com.example.instagram.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instagram.R;
import com.example.instagram.databinding.ActivitySettingsBinding;
import com.example.instagram.databinding.ActivityUploadPostBinding;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.store.Store;
import com.example.instagram.viewmodel.RegisterViewModel;
import com.example.instagram.viewmodel.SettingsViewModel;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel settingsViewModel;
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding.setName(Store.getUser().getName());
        binding.setLastname(Store.getUser().getLastName());
        Bundle extras = getIntent().getExtras();
        String imageFromCamera = extras.getString("image");
        if(imageFromCamera!=null){
            Picasso.get().load(RetrofitService.getBaseUrl()+"/api/getfile/profile/"+ imageFromCamera).into(binding.profilePic);
        }else{
            Picasso.get().load(RetrofitService.getBaseUrl()+"/api/getfile/profile/"+ Store.getUser().getProfilePic()).into(binding.profilePic);
        }
        binding.saveBtn.setOnClickListener(v -> {
            Log.d("xxx", String.valueOf(binding.name.getText()));
            settingsViewModel.updateUser(String.valueOf(binding.name.getText()), String.valueOf(binding.lastname.getText()), String.valueOf(binding.password.getText()), imageFromCamera!=null?imageFromCamera:Store.getUser().getProfilePic());
            finish();
        });
        binding.profilePic.setOnClickListener(v->{
            Intent camera = new Intent(SettingsActivity.this, CameraActivity.class);
            camera.putExtra("type", "profile");
            startActivity(camera);
            finish();
        });
        setContentView(view);
    }
}