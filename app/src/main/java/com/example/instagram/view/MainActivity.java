package com.example.instagram.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instagram.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        boolean ok = true;
//        if(ok){
//            Intent mainpage = new Intent(MainActivity.this, CameraActivity.class);
//
//            startActivity(mainpage);
//        }

        binding.loginBtn.setOnClickListener(v->{
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
           startActivity(login);
        });

        binding.registerBtn.setOnClickListener(v->{
            Intent register = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(register);
        });

    }


}