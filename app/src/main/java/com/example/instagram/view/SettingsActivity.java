package com.example.instagram.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.instagram.R;
import com.example.instagram.databinding.ActivitySettingsBinding;
import com.example.instagram.databinding.ActivityUploadPostBinding;
import com.example.instagram.store.Store;
import com.example.instagram.viewmodel.RegisterViewModel;
import com.example.instagram.viewmodel.SettingsViewModel;

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

        binding.saveBtn.setOnClickListener(v -> {
            Log.d("xxx", String.valueOf(binding.name.getText()));
            settingsViewModel.updateUser(String.valueOf(binding.name.getText()), String.valueOf(binding.lastname.getText()), String.valueOf(binding.password.getText()));
            finish();
        });
        setContentView(view);
    }
}