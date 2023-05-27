package com.example.instagram;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.instagram.databinding.PostComponentBinding;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostAdapter extends ArrayAdapter {
    private LayoutInflater inflater;



    public PostAdapter(Context context, List<String> data) {
        super(context, 0, data);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Inflate the list item layout using data binding
            PostComponentBinding binding = PostComponentBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        // Get the current item

        String imageUrl = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=460,height=600,fit=cover/animal/breed/pictures/613f5a373cb17614656987.jpg";
        ArrayList<String> tags = new ArrayList<>();
        tags.add("#tag");
        tags.add("#hash");
        tags.add("#xd");
        tags.add("#photo");
        tags.add("#pic");
        // Bind the data to the layout
        PostComponentBinding binding = (PostComponentBinding) convertView.getTag();
        binding.setUsername("username");
        binding.setLocation("location");
        Picasso.get().load(imageUrl).into(binding.imageView);
        Random random = new Random();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tags.forEach(e->{
                Chip chip = new Chip(getContext());
                chip.setText(e);
                chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256))));

                binding.tagsLayout.addView(chip);
            });
        }



        binding.executePendingBindings();

        return convertView;
    }
}
