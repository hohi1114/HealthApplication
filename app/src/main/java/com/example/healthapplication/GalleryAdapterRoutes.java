package com.example.healthapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class GalleryAdapterRoutes extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    private ArrayList<ImageModel> galleryList;

    public GalleryAdapterRoutes(ArrayList<ImageModel> galleryList) {
        this.galleryList = galleryList;
    }


    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryAdapter.GalleryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.GalleryViewHolder holder, int position) {
        final ImageModel imageModel = galleryList.get(position);

        Glide.with(holder.galleryImageView.getContext()).
                load(imageModel.getUrl())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.galleryImageView);

        // Set transition name same as the Image name

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }




}
