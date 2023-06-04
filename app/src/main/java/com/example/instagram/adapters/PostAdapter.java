package com.example.instagram.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.databinding.ItemPostBinding;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@UnstableApi public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> list;

    public PostAdapter(Context context, List<String> data) {
        inflater = LayoutInflater.from(context);
        this.list = data;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout using data binding
        ItemPostBinding binding = ItemPostBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the current item
        String link = list.get(position);
        int lastDotIndex = link.lastIndexOf(".");
        String type = link.substring(lastDotIndex + 1);
        holder.binding.setUsername("username");
        holder.binding.setLocation("location");

        if (type.equals("jpg")) {
            ImageView iv = new ImageView(holder.itemView.getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER);
            iv.setAdjustViewBounds(true);
            holder.binding.mediaView.addView(iv);
            Picasso.get().load(list.get(position)).into(iv);
        } else {
            ExoPlayer player = new ExoPlayer.Builder(holder.itemView.getContext()).build();
            PlayerView playerView = new PlayerView(holder.itemView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            MediaItem mediaItem = MediaItem.fromUri(list.get(position));

            playerView.setLayoutParams(params);
            playerView.setControllerHideOnTouch(true);
            playerView.setUseController(false);
//            playerView.setMinimumHeight(500);
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
            playerView.setOnFocusChangeListener((v,hasFocus)->{
                if(hasFocus) {

                }else{

                }
            });
            playerView.setOnClickListener(v->{

            if(playing.get()){
                player.pause();
                playing.set(false);
            }else{
                player.play();
                playing.set(true);
            }
            });
//            player.play();

            holder.binding.mediaView.addView(playerView);
        }

        Random random = new Random();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<String> tags = Arrays.asList("#love", "#instagood", "#photooftheday", "#beautiful", "#tbt");
            tags.forEach(e -> {
                Chip chip = new Chip(holder.itemView.getContext());
                chip.setText(e);
                chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))));

                holder.binding.tagsLayout.addView(chip);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}