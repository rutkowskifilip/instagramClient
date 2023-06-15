package com.example.instagram.view;

import androidx.annotation.OptIn;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.R;
import com.example.instagram.adapters.PostAdapter;
import com.example.instagram.databinding.ActivityMainPageBinding;
import com.example.instagram.databinding.FragmentPostListBinding;
import com.example.instagram.databinding.FragmentProfileBinding;
import com.example.instagram.model.Post;
import com.example.instagram.viewmodel.PostListViewModel;
import com.example.instagram.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UnstableApi public class PostListFragment extends Fragment{

    private PostListViewModel postListViewModel;

    public static PostListFragment newInstance() {
        return new PostListFragment();
    }
    FragmentPostListBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPostListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        postListViewModel = new ViewModelProvider(this).get(PostListViewModel.class);
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

        postListViewModel.getPosts();
        postListViewModel.getObservedPosts().observe(getViewLifecycleOwner(), s->{
            if(s != null){
                ArrayList<Post> posts = s;
                RecyclerView listView = binding.listView;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                listView.setLayoutManager(layoutManager);
                Collections.reverse(posts);
                PostAdapter adapter = new PostAdapter(getContext(), posts);

                listView.setAdapter(adapter);
            }
        });
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        String imageUrl = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=460,height=600,fit=cover/animal/breed/pictures/613f5a373cb17614656987.jpg";
        String imageUrl2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        String imageUrl3 = "https://apetete.pl/blog/wp-content/uploads/2017/03/brytyjczyki-koty.png";
        String imageUrl4 = "https://hodowlakotybrytyjskie.pl/wordpress/wp-content/uploads/2023/01/IMG_71721-scaled.jpg";
        List<String> data = Arrays.asList(imageUrl2, imageUrl, imageUrl3, imageUrl4, imageUrl2,imageUrl3);

        return view;
    }



}