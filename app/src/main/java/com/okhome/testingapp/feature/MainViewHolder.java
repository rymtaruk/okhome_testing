package com.okhome.testingapp.feature;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
            binding.ivAvatar.setVisibility(View.INVISIBLE);
            Glide.with(binding.getRoot())
                    .load(data.getSrc().getTiny())
                    .centerCrop()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.sflLoading.setVisibility(View.GONE);
                            binding.ivAvatar.setImageResource(R.drawable.ic_twotone_image_100);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.sflLoading.setVisibility(View.GONE);
                            binding.ivAvatar.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(binding.ivAvatar);
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
            binding.ivAvatar.setVisibility(View.INVISIBLE);
            Glide.with(binding.getRoot())
                    .load(data.getSrc().getTiny())
                    .centerCrop()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.sflLoading.setVisibility(View.GONE);
                            binding.ivAvatar.setImageResource(R.drawable.ic_twotone_image_80);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.sflLoading.setVisibility(View.GONE);
                            binding.ivAvatar.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(binding.ivAvatar);
            binding.tvInfo.setText(data.getPhotographer());
        }
    }
}
