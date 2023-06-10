package com.example.instagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.instagram.databinding.ItemPhotoBinding;
import com.example.instagram.databinding.ItemProfilePostBinding;
import com.example.instagram.helpers.OnItemClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> photos;
    private OnItemClick mCallback;
    public PhotoAdapter(Context context, ArrayList<String> photos, OnItemClick listener) {
        this.context = context;
        this.photos = photos;
        this.mCallback = listener;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public String getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemPhotoBinding binding;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = ItemPhotoBinding.inflate(inflater, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }

        String photo = photos.get(position);
        Picasso.get().load(photo).into(binding.imageView);
        binding.getRoot().setOnClickListener(v->{
            mCallback.onClick(photo);
        });
        return binding.getRoot();
    }
}
