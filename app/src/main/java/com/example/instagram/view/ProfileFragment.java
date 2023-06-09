package com.example.instagram.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.instagram.R;
import com.example.instagram.adapters.ProfilePostAdapter;
import com.example.instagram.databinding.FragmentProfileBinding;
import com.example.instagram.databinding.ItemPostBinding;
import com.example.instagram.model.Post;
import com.example.instagram.service.RetrofitService;
import com.example.instagram.viewmodel.ProfileViewModel;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProfileFragment extends Fragment implements ProfilePostAdapter.OnPostListener{

    private ProfileViewModel profileViewModel;
    private ArrayList<Post> images = new ArrayList<>();
    FragmentProfileBinding binding;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        // Check if the activity is not null
        if (activity != null) {
            // Get the action bar
            ActionBar actionBar = activity.getSupportActionBar();

            // Check if the action bar is not null
            if (actionBar != null) {
                // Hide the action bar
                actionBar.show();
            }
        }

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getProfile();

        profileViewModel.getObservedUser().observe(getViewLifecycleOwner(), s->{
            if(s != null){

                Log.d("xxx","lastanem " + s.getLastName());
                getActivity().setTitle(s.getUsername());
                profileViewModel.getProfilePhotos(s.getUsername());
                binding.setName(s.getName());
                binding.setSurname(s.getLastName());


            }else{
                Log.d("xxx", "incorrect data");
            }
        });

        profileViewModel.getObservedPosts().observe(getViewLifecycleOwner(),s->{
            if(s!= null){
                Log.d("xxx", String.valueOf(s.size()));
                binding.setPosts(String.valueOf(s.size()));
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
                binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
                ProfilePostAdapter adapter = new ProfilePostAdapter(s, this);
                binding.recyclerView.setAdapter(adapter);
                images = s;
            }
        });

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

        binding.setAge(String.valueOf(18));
        binding.setBio("Hello i am instagram app user. Like sport and programming. Love cats. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        binding.setPosts(String.valueOf(images.size()));
        binding.setFollowers(formattedFollowers);
        binding.setFollowing(formattedFollowing);



        return view;
    }

    @Override
    public void onPostClick(int position) {
        Post image = images.get(position);

//        Log.d("xxx", image);
        Dialog dialog = new Dialog(getContext());



        ItemPostBinding postBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.item_post, null,false);
        List<String> tags = Arrays.asList("#love", "#instagood", "#photooftheday", "#beautiful", "#tbt");

        postBinding.setUsername(image.getAlbum());
        postBinding.setLocation("location");
//        Picasso.get().load(RetrofitService.getBaseUrl()+"/api/getfile/"+image.getId()).into(postBinding.imageView);
        ImageView iv = new ImageView(postBinding.mediaView.getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        iv.setAdjustViewBounds(true);
        postBinding.mediaView.addView(iv);
        Picasso.get().load(RetrofitService.getBaseUrl()+"/api/getfile/"+image.getId()).into(iv);
        Random random = new Random();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tags.forEach(e->{
                Chip chip = new Chip(getContext());
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