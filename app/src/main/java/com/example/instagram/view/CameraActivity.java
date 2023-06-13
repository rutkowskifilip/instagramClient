package com.example.instagram.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.instagram.databinding.ActivityCameraBinding;
import com.example.instagram.store.Store;
import com.example.instagram.viewmodel.CameraViewModel;
import com.example.instagram.viewmodel.UploadPostViewModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CameraActivity extends AppCompatActivity {
    ActivityCameraBinding binding;
    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    private int PERMISSIONS_REQUEST_CODE = 100;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private ImageCapture imageCapture;

    private VideoCapture videoCapture;
    ProcessCameraProvider cameraProvider;
CameraViewModel cameraViewModel;
    private boolean isRecording = true;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().hide();
        setContentView(view);
        if (!checkIfPermissionsGranted()) {
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        } else {
            cameraStart();
        }
        cameraViewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        binding.takePhotoBtn.setOnClickListener(v -> {
            String timestamp = String.valueOf(new Date().getTime());
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");


            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(
                            getContentResolver(),
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues)
                            .build();
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            if(extras.get("type").equals("post")) {
                                Intent intent = new Intent(CameraActivity.this, UploadPostActivity.class);
                                intent.putExtra("uri", outputFileResults.getSavedUri().toString());
                                intent.putExtra("type", "image");
                                intent.putExtra("timestamp", timestamp);
                                startActivity(intent);
                            }else{
                                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + timestamp + ".jpg");
                                RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
                                RequestBody album = RequestBody.create(MultipartBody.FORM, Store.getUser().getEmail()); //TODO
                                cameraViewModel.sendPost(album,body);

                            }

                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            // error
                        }
                    });
        });
        binding.takePhotoBtn.setOnLongClickListener((v) -> {
            isRecording = !isRecording;

            if (!isRecording) {
                recordVideo();
                binding.setIsRecording(true);

            } else {
                videoCapture.stopRecording();
                binding.setIsRecording(false);
            }

            return true;
        });
        cameraViewModel.getObservedPost().observe(this, s->{
                if(s!=null){
                    Intent settings = new Intent(CameraActivity.this, SettingsActivity.class);
                    settings.putExtra("image", String.valueOf(s.getId()));
                    startActivity(settings);
                    finish();

                };
        });

    }
    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraStart();
                } else {
                    //nie
                }
                break;

        }

    }

    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void recordVideo() {
        String timestamp = String.valueOf(new Date().getTime());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");

        videoCapture.startRecording(
                new VideoCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                ContextCompat.getMainExecutor(this),
                new VideoCapture.OnVideoSavedCallback() {
                    @Override
                    public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                        Intent intent = new Intent(CameraActivity.this, UploadPostActivity.class);
                        intent.putExtra("uri", outputFileResults.getSavedUri().toString());
                        intent.putExtra("type", "video");
                        intent.putExtra("timestamp", timestamp);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        // error
                    }
                });


    }

    public void cameraStart() {
        cameraProviderFuture = ProcessCameraProvider.getInstance((this));
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (InterruptedException | ExecutionException e) {
                // No errors need to be handled for this Future. This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @SuppressLint("RestrictedApi")
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(binding.camera.getDisplay().getRotation())
                        .build();
        videoCapture = new VideoCapture.Builder()
                .setTargetRotation(binding.camera.getDisplay().getRotation())
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.camera.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, videoCapture,  preview);
    }
}