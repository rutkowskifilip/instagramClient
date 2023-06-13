package com.example.instagram.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
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
import com.example.instagram.model.Post;
import com.example.instagram.service.RetrofitService;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@UnstableApi public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Post> list;


    public PostAdapter(Context context, ArrayList<Post> data) {
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

        Post post = list.get(position);
        String url = post.getUrl();
        int lastDotIndex = url.lastIndexOf(".");
        String type = url.substring(lastDotIndex + 1);


            Call<Map<String,String>> call = RetrofitService.getUserAPI().find(post.getAlbum());
            call.enqueue(new Callback<Map<String,String>>() {
                @Override
                public void onResponse(Call<Map<String,String>> call, Response<Map<String,String >> response) {
                    Log.d("xxx", response.body().get("user"));
                    holder.binding.setUsername(response.body().get("user"));
                    Picasso.get().load(RetrofitService.getBaseUrl()+"/api/getfile/profile/"+ response.body().get("profilePic")).into(holder.binding.profilePic);
                }

                @Override
                public void onFailure(Call<Map<String,String>> call, Throwable t) {
                    Log.d("xxx", "er " +t.getMessage());
                }
            });


        holder.binding.setLocation(post.getLocation());
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
            if(post.getLastChange().equals("original")) {
                Picasso.get().load(RetrofitService.getBaseUrl() + "/api/getfile/" + post.getId()).into(iv);
            }else {
                Picasso.get().load(RetrofitService.getBaseUrl() + "/api/getfile/" + post.getId()+"/"+post.getLastChange()).into(iv);
            }

        } else {
            ExoPlayer player = new ExoPlayer.Builder(holder.itemView.getContext()).build();
            PlayerView playerView = new PlayerView(holder.itemView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            MediaItem mediaItem = MediaItem.fromUri(RetrofitService.getBaseUrl()+"/api/getfile/"+ post.getId());

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
            post.getTags().forEach(e -> {
                Chip chip = new Chip(holder.itemView.getContext());
                chip.setText(e.get("name"));
                chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,10,0);
                chip.setLayoutParams(layoutParams);
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