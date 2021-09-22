package com.okhome.testingapp.feature;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.okhome.testingapp.R;
import com.okhome.testingapp.databinding.ItemGridBinding;
import com.okhome.testingapp.databinding.ItemListBinding;
import com.okhome.testingapp.model.PhotoData;

public class MainViewHolder {

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        private final ItemGridBinding binding;

        public GridViewHolder(@NonNull ItemGridBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(PhotoData data) {
            Glide.with(binding.getRoot())
                    .asBitmap()
                    .load(data.getSrc().getTiny())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .centerCrop()
                    .placeholder(R.drawable.bg_shimmer_child)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            binding.ivAvatar.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            binding.ivAvatar.setImageDrawable(placeholder);
                        }
                    });
            binding.tvInfo.setText(data.getPhotographer());
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final ItemListBinding binding;

        public ListViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(PhotoData data){
            Glide.with(binding.getRoot())
                    .asBitmap()
                    .load(data.getSrc().getTiny())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .centerCrop()
                    .placeholder(R.drawable.bg_shimmer_child)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            binding.ivAvatar.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            binding.ivAvatar.setImageDrawable(placeholder);
                        }
                    });
            binding.tvInfo.setText(data.getPhotographer());
        }
    }
}
