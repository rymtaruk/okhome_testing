package com.okhome.testingapp.feature;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

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
    @Override
    public int getItemViewType(int position) {
        int spanCount = gridLayoutManager.getSpanCount();
        if (spanCount == LIST_SPAN){
            return LIST_TYPE;
        } else {
            return GRID_TYPE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinding viewBinding;
        if (viewType == LIST_TYPE){
            viewBinding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            viewBinding = ItemGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.bind(getItems().get(position));
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private int view;
        private ItemListBinding listBinding;
        private ItemGridBinding gridBinding;
        public ViewHolder(@NonNull ViewBinding itemView, int viewType) {
            super(itemView.getRoot());
            view = viewType;
            if (view == LIST_TYPE){
                listBinding = (ItemListBinding) itemView;
            } else {
                gridBinding = (ItemGridBinding) itemView;
            }
        }

        private void bind(PhotoData data){
            if (view == LIST_TYPE){
                listBinding.tvInfo.setText(data.getPhotographer());
            } else {
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
}
