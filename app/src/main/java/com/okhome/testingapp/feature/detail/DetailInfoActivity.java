package com.okhome.testingapp.feature.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.okhome.testingapp.R;
import com.okhome.testingapp.base.BaseActivity;
import com.okhome.testingapp.databinding.ActivityDetailInfoBinding;

public class DetailInfoActivity extends BaseActivity {
    public static final String PHOTO_ID = "PHOTO_ID";
    private ActivityDetailInfoBinding binding;
    private DetailInfoViewModel viewModel;

    @Override
    protected void onSetup() {
        binding = ActivityDetailInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = getViewModelProvider().get(DetailInfoViewModel.class);

        Intent extraIntent = getIntent();
        if (extraIntent != null) {
            int photoId = extraIntent.getIntExtra(PHOTO_ID, 0);
            viewModel.loadDetailInfo(photoId);
        }
    }

    @Override
    protected void onViewCreated() {
        setSupportActionBar(binding.toolbar);
        binding.toolbarLayout.setTitle("Detail");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onObserverData() {
        viewModel.getPhotoData().observe(this, data -> {
            Glide.with(this).load(data.getSrc().getLandscape()).centerCrop().listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    binding.ivPhoto.setVisibility(View.VISIBLE);
                    binding.ivPhoto.setImageResource(R.drawable.ic_twotone_image_100);
                    binding.sflImage.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    binding.ivPhoto.setVisibility(View.VISIBLE);
                    binding.sflImage.setVisibility(View.GONE);
                    return false;
                }
            }).into(binding.ivPhoto);
            binding.toolbarLayout.setTitle("Detail " + data.getPhotographer());
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}