package com.okhome.testingapp.feature;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.okhome.connection.utils.RecyclerViewListener;
import com.okhome.testingapp.R;
import com.okhome.testingapp.databinding.ItemGridBinding;
import com.okhome.testingapp.databinding.ItemListBinding;
import com.okhome.testingapp.model.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    public static final int LIST_SPAN = 1;
    public static final int GRID_SPAN = 3;

    public static final int LIST_TYPE = 1;
    public static final int GRID_TYPE = 2;

    private List<PhotoData> items;
    private GridLayoutManager gridLayoutManager;
    private RecyclerViewListener<PhotoData> listener;

    @Override
    public int getItemViewType(int position) {
        int spanCount = getGridLayoutManager().getSpanCount();
        if (spanCount == LIST_SPAN) {
            return LIST_TYPE;
        } else {
            return GRID_TYPE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding viewBinding;
        if (viewType == LIST_TYPE) {
            viewBinding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            viewBinding = ItemGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        return new ViewHolder(viewBinding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.bind(getItems().get(position));
        holder.itemView.getRoot().setOnClickListener(v -> getListener().onClick(v, getItems().get(position)));
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final int view;
        private ViewBinding itemView;
        private ItemListBinding listBinding;
        private ItemGridBinding gridBinding;

        public ViewHolder(@NonNull ViewBinding itemView, int viewType) {
            super(itemView.getRoot());
            this.view = viewType;
            this.itemView = itemView;
        }

        private void bind(PhotoData data) {
            if (view == LIST_TYPE) {
                listBinding = (ItemListBinding) itemView;
                listBinding.ivAvatar.setVisibility(View.INVISIBLE);
                Glide.with(listBinding.getRoot())
                        .load(data.getSrc().getTiny())
                        .centerCrop()
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                listBinding.sflLoading.setVisibility(View.GONE);
                                listBinding.ivAvatar.setImageResource(R.drawable.ic_twotone_image_80);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                listBinding.sflLoading.setVisibility(View.GONE);
                                listBinding.ivAvatar.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(listBinding.ivAvatar);
                listBinding.tvInfo.setText(data.getPhotographer());
            } else {
                gridBinding = (ItemGridBinding) itemView;
                gridBinding.ivAvatar.setVisibility(View.INVISIBLE);
                Glide.with(gridBinding.getRoot())
                        .load(data.getSrc().getTiny())
                        .centerCrop()
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                gridBinding.sflLoading.setVisibility(View.GONE);
                                gridBinding.ivAvatar.setImageResource(R.drawable.ic_twotone_image_100);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                gridBinding.sflLoading.setVisibility(View.GONE);
                                gridBinding.ivAvatar.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(gridBinding.ivAvatar);
                gridBinding.tvInfo.setText(data.getPhotographer());
            }
        }

    }

    public List<PhotoData> getItems() {
        if (items == null) {
            items = new ArrayList<>(0);
        }
        return items;
    }

    public void setItems(List<PhotoData> items) {
        this.items = items;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public RecyclerViewListener<PhotoData> getListener() {
        return listener;
    }

    public void setListener(RecyclerViewListener<PhotoData> listener) {
        this.listener = listener;
    }
}
