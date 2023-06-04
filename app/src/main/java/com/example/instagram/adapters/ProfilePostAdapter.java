package com.example.instagram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {
    private ArrayList<String> list;
    private OnPostListener mOnPostListener;
    public ProfilePostAdapter(ArrayList images, OnPostListener onPostListener) {
        this.list = images;
        this.mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ProfilePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_post, parent, false);

        return new ViewHolder(v, mOnPostListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostAdapter.ViewHolder holder, int position) {
        String res = list.get(position);
        Picasso
                .get()
                .load(res)
                .into(holder.image);


//        holder.image.setImageResource(res);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        OnPostListener onPostListener;
        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            int h = new Random().nextInt(401) + 400;
            image.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));
            this.onPostListener = onPostListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }
    public interface OnPostListener{
            void onPostClick(int position);
    }




}


