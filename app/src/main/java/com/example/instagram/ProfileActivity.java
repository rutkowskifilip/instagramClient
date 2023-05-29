package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.instagram.databinding.ActivityProfileBinding;
import com.example.instagram.databinding.ItemPostBinding;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity implements ProfilePostAdapter.OnPostListener{
    private ActivityProfileBinding binding;
    private ArrayList images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("username");

        binding.bottomNavigation.setSelectedItemId(R.id.profilPage);
        binding.bottomNavigation.setOnItemSelectedListener(v -> {
            Log.d("xxx", String.valueOf(v));
            if(String.valueOf(v).equals("home")){
                Intent profile = new Intent(ProfileActivity.this, MainPageActivity.class);
                startActivity(profile);
            }
            return false;
        });
        String imageUrl = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=460,height=600,fit=cover/animal/breed/pictures/613f5a373cb17614656987.jpg";
        String imageUrl2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Gandu%C5%9B.jpg/640px-Gandu%C5%9B.jpg";
        String imageUrl3 = "https://apetete.pl/blog/wp-content/uploads/2017/03/brytyjczyki-koty.png";
        String imageUrl4 = "https://hodowlakotybrytyjskie.pl/wordpress/wp-content/uploads/2023/01/IMG_71721-scaled.jpg";

        images = new ArrayList<String>(Arrays.asList(
                imageUrl, imageUrl2, imageUrl3,imageUrl4, imageUrl, imageUrl2,imageUrl3, imageUrl4, imageUrl, imageUrl2,imageUrl3, imageUrl4
                ));
        int followers = 123456;
        int following = 12345;
        String formattedFollowers = followers < 1000 ? String.valueOf(followers) :
                followers < 10000 ? new DecimalFormat("#0.00").format(followers / 1000f) + "k" :
                        followers < 100000 ? new DecimalFormat("#0.0").format(followers / 1000f) + "k" :
                                new DecimalFormat("#0").format(followers / 1000f) + "k";
        String formattedFollowing = following < 1000 ? String.valueOf(following) :
                following < 10000 ? new DecimalFormat("#0.00").format(following / 1000f) + "k" :
                        following < 100000 ? new DecimalFormat("#0.0").format(following / 1000f) + "k" :
                                new DecimalFormat("#0").format(following / 1000f) + "k";
        binding.setSurname("Surname");
        binding.setAge(String.valueOf(18));
        binding.setBio("Hello i am instagram app user. Like sport and programming. Love cats. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        binding.setPosts(String.valueOf(images.size()));
        binding.setFollowers(formattedFollowers);
        binding.setFollowing(formattedFollowing);
//        ArrayList images = new ArrayList<>(Arrays.asList(
//                R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,  R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,  R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img
//        ));
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
        binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ProfilePostAdapter adapter = new ProfilePostAdapter(images, this);
        binding.recyclerView.setAdapter(adapter);



    }

    @Override
    public void onPostClick(int position) {
        String image = (String) images.get(position);
        Log.d("xxx", image);
        Dialog dialog = new Dialog(this);



        ItemPostBinding postBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.item_post, null,false);
        List<String> tags = Arrays.asList("#love", "#instagood", "#photooftheday", "#beautiful", "#tbt");

        postBinding.setUsername("username");
        postBinding.setLocation("location");
//        Picasso.get().load(image).into(postBinding.imageView);
        Random random = new Random();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tags.forEach(e->{
                Chip chip = new Chip(this);
                chip.setText(e);
                chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256))));

                postBinding.tagsLayout.addView(chip);
            });
        }
        dialog.setContentView(postBinding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = (int) (binding.scrollView.getWidth()*0.9);
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Intent intent = new Intent(this, MainPageActivity.class);
//        startActivity(intent);
    }
    public class CustomDialogFragment extends DialogFragment {
        /** The system calls this to get the DialogFragment's layout, regardless
         of whether it's being displayed as a dialog or an embedded fragment. */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout to use as dialog or embedded fragment
            return inflater.inflate(R.layout.item_post, container, false);
        }

        /** The system calls this only when creating the layout in a dialog. */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // The only reason you might override this method when using onCreateView() is
            // to modify any dialog characteristics. For example, the dialog includes a
            // title by default, but your custom layout might not need it. So here you can
            // remove the dialog title, but you must call the superclass to get the Dialog.
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            return dialog;
        }
    }
//
}
