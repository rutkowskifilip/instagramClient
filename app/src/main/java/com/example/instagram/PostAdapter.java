package com.example.instagram;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;


import com.example.instagram.databinding.ItemPostBinding;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.xml.transform.sax.SAXResult;

public class PostAdapter extends ArrayAdapter {
    private LayoutInflater inflater;

    private List<String> list;

    public PostAdapter(Context context, List<String> data) {
        super(context, 0, data);
        inflater = LayoutInflater.from(context);
        this.list = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Inflate the list item layout using data binding
            ItemPostBinding binding = ItemPostBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        // Get the current item


        List<String> tags = Arrays.asList("#love", "#instagood", "#photooftheday", "#beautiful", "#tbt");
        // Bind the data to the layout
        ItemPostBinding binding = (ItemPostBinding) convertView.getTag();
        binding.setUsername("username");
        binding.setLocation("location");
        String link = list.get(position);
        int lastDotIndex = link.lastIndexOf(".");

        String type = link.substring(lastDotIndex + 1);

        if(type.equals("jpg")) {
            ImageView iv = new ImageView(getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER);
            iv.setAdjustViewBounds(true);
            binding.mediaView.addView(iv);
            Picasso.get().load(list.get(position)).into(iv);
        }else{
            ExoPlayer player = new ExoPlayer.Builder(getContext()).build();
            PlayerView playerView = new PlayerView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            MediaItem mediaItem = MediaItem.fromUri(list.get(position));

            playerView.setLayoutParams(params);
            playerView.setControllerHideOnTouch(true);

//            playerView.setMinimumHeight(500);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            playerView.setPlayer(player);
            player.setMediaItem(mediaItem);
            player.prepare();
//            player.play();

            binding.mediaView.addView(playerView);
        }
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
