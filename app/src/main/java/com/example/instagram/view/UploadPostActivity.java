package com.example.instagram.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.instagram.databinding.ActivityUploadPostBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class UploadPostActivity extends AppCompatActivity {
    ActivityUploadPostBinding binding;
    private ChipGroup chipGroup;
    private TextInputEditText textInputEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("New post");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String photo = extras.getString("uri");
            Picasso.get().load(photo).into(binding.imageView);
            // Use the value as needed
        }
        chipGroup = binding.chipGroup;
        textInputEditText = binding.textInputEditText;

        textInputEditText.addTextChangedListener(new TextWatcher() {
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
                        addChip(chipText);
                        textInputEditText.setText("");
                    }
                }
            }
        });

    }
    private void addChip(String text) {
        Chip chip = new Chip(this);
        chip.setText(text);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
            }
        });

        chipGroup.addView(chip);
    }
}
