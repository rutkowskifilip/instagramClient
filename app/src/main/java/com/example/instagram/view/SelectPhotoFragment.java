package com.example.instagram.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.example.instagram.R;
import com.example.instagram.adapters.PhotoAdapter;
import com.example.instagram.databinding.FragmentSelectPhotoBinding;
import com.example.instagram.helpers.OnItemClick;

import com.example.instagram.viewmodel.SelectPhotoViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class SelectPhotoFragment extends Fragment implements OnItemClick {

    private SelectPhotoViewModel selectPhotoViewModel;
    String selectedPhoto;


    public static SelectPhotoFragment newInstance() {
        return new SelectPhotoFragment();
    }

    FragmentSelectPhotoBinding binding;
    private MaterialToolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        // Check if the activity is not null
        if (activity != null) {
            // Get the action bar
            ActionBar actionBar = activity.getSupportActionBar();

            // Check if the action bar is not null
            if (actionBar != null) {
                // Hide the action bar
                actionBar.hide();
            }
        }
        toolbar = binding.topAppBar;
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        // Set up the button click listener
        toolbar.setOnMenuItemClickListener(v-> {
              Intent upload = new Intent(getContext(), UploadPostActivity.class);
              upload.putExtra("photo", selectedPhoto);
              startActivity(upload);
            return false;
        });


        ArrayList<String> photos = new ArrayList<String>();
        String imageUrl3 = "https://apetete.pl/blog/wp-content/uploads/2017/03/brytyjczyki-koty.png";
        String imageUrl4 = "https://hodowlakotybrytyjskie.pl/wordpress/wp-content/uploads/2023/01/IMG_71721-scaled.jpg";
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        photos.add(imageUrl3);
        photos.add(imageUrl4);
        selectedPhoto = photos.get(0);


        Picasso.get().load(selectedPhoto).into(binding.selectedPhoto);
        PhotoAdapter adapter = new PhotoAdapter(getContext(), photos, this);

        binding.photoGallery.setAdapter(adapter);
//        Log.d("xxx", String.valueOf(photos));
//        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);
        binding.cameraBtn.setOnClickListener(v->{
            Intent camera = new Intent(getContext(), CameraActivity.class);
            startActivity(camera);
        });
        return view;
    }

    private ArrayList<File> getPictureFiles() {
        ArrayList<File> pictureFiles = new ArrayList<>();

        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File[] files = picturesDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Log.d("xxx", "here");
                    pictureFiles.add(file);
                }
            }
        }

        return pictureFiles;

    }


    @Override
    public void onClick(String val) {
        selectedPhoto = val;

        Picasso.get().load(selectedPhoto).into(binding.selectedPhoto);
    }

}



