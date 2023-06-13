package com.example.instagram.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;

import com.example.instagram.R;
import com.example.instagram.api.FiltersAPI;
import com.example.instagram.databinding.ActivityUploadPostBinding;
import com.example.instagram.databinding.DialogFiltersBinding;
import com.example.instagram.databinding.ItemAddTagsBinding;
import com.example.instagram.requests.FilterRequest;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.store.Store;
import com.example.instagram.viewmodel.MapsViewModel;
import com.example.instagram.viewmodel.UploadPostViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@UnstableApi
public class UploadPostActivity extends AppCompatActivity {
    ActivityUploadPostBinding binding;

    private int PHOTO_ID;
    private UploadPostViewModel uploadPostViewModel;
    private MapsViewModel mapsViewModel;
    private ArrayList<Map<String, String>> tags;
    private String location;
    private MaterialToolbar toolbar;
    private String type;
    private String lastChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        toolbar = binding.topAppBar;
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        // Set up the button click listener
        toolbar.setOnMenuItemClickListener(v -> {
            Intent main = new Intent(UploadPostActivity.this, MainPageActivity.class);

            startActivity(main);
            return false;
        });
        uploadPostViewModel = new ViewModelProvider(this).get(UploadPostViewModel.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("type");
            String timestamp = extras.getString("timestamp");
            uploadFile(type, timestamp);

        }

        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        uploadPostViewModel.getObservedPost().observe(this, s -> {
            if (s != null) {
                PHOTO_ID = Integer.parseInt(s.getId());
                tags = s.getTags();
                lastChange = s.getLastChange();
                location = s.getLocation();
                binding.location.setText(location);
                loadMedia(type);
                updateTags();
            }

        });
        if (!type.equals("image")) {
            binding.addFilterBtn.setVisibility(View.GONE);
        }

        binding.addFilterBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            DialogFiltersBinding binding = DialogFiltersBinding.inflate(LayoutInflater.from(this));
            View dialogView = binding.getRoot();
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();
            binding.cancelBtn.setOnClickListener(l -> {
                dialog.dismiss();
            });
            binding.flipBtn.setOnClickListener(l -> {
                FilterRequest filterRequest = new FilterRequest(PHOTO_ID, "flip");
                uploadPostViewModel.filter(filterRequest);
                dialog.dismiss();
            });
            binding.flopBtn.setOnClickListener(l -> {
                FilterRequest filterRequest = new FilterRequest(PHOTO_ID, "flop");
                uploadPostViewModel.filter(filterRequest);
                dialog.dismiss();
            });
            binding.rotateBtn.setOnClickListener(l -> {
                FilterRequest filterRequest = new FilterRequest(PHOTO_ID, "rotate");
                uploadPostViewModel.filter(filterRequest);
                dialog.dismiss();
            });
            binding.negateBtn.setOnClickListener(l -> {
                FilterRequest filterRequest = new FilterRequest(PHOTO_ID, "negate");
                uploadPostViewModel.filter(filterRequest);
                dialog.dismiss();
            });
            binding.grayBtn.setOnClickListener(l -> {
                FilterRequest filterRequest = new FilterRequest(PHOTO_ID, "grayscale");
                uploadPostViewModel.filter(filterRequest);
                dialog.dismiss();
            });


        });

        binding.addTagBtn.setOnClickListener(v -> {
            openTagDialog();
        });
        binding.addLocationBtn.setOnClickListener(v -> {
            Intent maps = new Intent(UploadPostActivity.this, MapsActivity.class);
            maps.putExtra("id", PHOTO_ID);
            startActivity(maps);
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        uploadPostViewModel = new ViewModelProvider(this).get(UploadPostViewModel.class);
        uploadPostViewModel.getObservedPost().observe(this, s -> {
            if (s != null) {
                Log.d("xxx", "onWindowFocusChanged: ");
                PHOTO_ID = Integer.parseInt(s.getId());
                tags = s.getTags();
                lastChange = s.getLastChange();
                Log.d("xxx", uploadPostViewModel.getObservedPost().getValue().getLocation());
                location = uploadPostViewModel.getObservedPost().getValue().getLocation();
                binding.location.setText(location);
                updateTags();
            }
        });

    }


    private void updateTags() {
        binding.tags.removeAllViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tags.forEach(e -> {
                Chip chip = new Chip(this);
                chip.setText(e.get("name"));
                binding.tags.addView(chip);
            });
        }

    }

    private void openTagDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ItemAddTagsBinding tagsBinding = ItemAddTagsBinding.inflate(LayoutInflater.from(this));
        builder.setView(tagsBinding.getRoot());
        builder.setTitle("Add tags");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tags.forEach(e -> {
                Chip chip = new Chip(this);
                chip.setText(e.get("name"));
                tagsBinding.chipGroup.addView(chip);
            });
        }
        tagsBinding.textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString();
                if (!inputText.isEmpty()) {
                    if (inputText.endsWith(" ") || inputText.endsWith("\n")) {
                        String chipText = inputText.substring(0, inputText.length() - 1);
                        Chip chip = new Chip(UploadPostActivity.this);
                        chip.setText(chipText);
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tagsBinding.chipGroup.removeView(chip);
                            }
                        });

                        tagsBinding.chipGroup.addView(chip);
                        tagsBinding.textInputEditText.setText("");
                    }
                }
            }
        });
        builder.setPositiveButton("Save", (dialog, which) -> {
            saveTags(tagsBinding.chipGroup);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Handle the Cancel button click
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void saveTags(ChipGroup chipGroup) {
        ArrayList<String> tags = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            tags.add(String.valueOf(chip.getText()));

        }

        uploadPostViewModel.addTags(PHOTO_ID, tags);
    }

    private void uploadFile(String type, String timestamp) {

        File file = new File(type.equals("video") ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/" + timestamp + ".mp4" : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + timestamp + ".jpg");
        RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
        RequestBody album = RequestBody.create(MultipartBody.FORM, Store.getUser().getEmail());
        Log.d("xxx", Store.getUser().getUsername() + " " + file);
        uploadPostViewModel.sendPost(album, body);
    }

    private void loadMedia(String type) {
        if (type.equals("image")) {
            ImageView iv = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            binding.mediaView.addView(iv);

            if (lastChange.equals("original")) {
                Picasso.get().load(RetrofitService.getBaseUrl() + "/api/getfile/" + PHOTO_ID).into(iv);
            } else {
                Picasso.get().load(RetrofitService.getBaseUrl() + "/api/getfile/" + PHOTO_ID + "/" + lastChange).into(iv);
            }
        } else {
            ExoPlayer player = new ExoPlayer.Builder(this).build();
            PlayerView playerView = new PlayerView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            MediaItem mediaItem = MediaItem.fromUri(RetrofitService.getBaseUrl() + "/api/getfile/" + PHOTO_ID);
            playerView.setLayoutParams(params);
            playerView.setControllerHideOnTouch(true);
            playerView.setUseController(false);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            playerView.setPlayer(player);
            player.setMediaItem(mediaItem);
            player.prepare();
            AtomicBoolean playing = new AtomicBoolean(false);
            playerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(@NonNull View view) {
                    player.play();
                    playing.set(true);
                }

                @Override
                public void onViewDetachedFromWindow(@NonNull View view) {
                    player.pause();
                    playing.set(false);
                }
            });
            playerView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {

                } else {

                }
            });
            playerView.setOnClickListener(v -> {

                if (playing.get()) {
                    player.pause();
                    playing.set(false);
                } else {
                    player.play();
                    playing.set(true);
                }
            });
            binding.mediaView.addView(playerView);
        }
    }


}
