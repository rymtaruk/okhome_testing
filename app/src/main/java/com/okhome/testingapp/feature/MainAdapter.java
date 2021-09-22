package com.okhome.testingapp.feature;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.okhome.connection.utils.RecyclerViewListener;
import com.okhome.testingapp.databinding.ItemGridBinding;
import com.okhome.testingapp.databinding.ItemListBinding;
import com.okhome.testingapp.model.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LIST_TYPE) {
            return new MainViewHolder.ListViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new MainViewHolder.GridViewHolder(ItemGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainViewHolder.GridViewHolder) {
            ((MainViewHolder.GridViewHolder) holder).bind(getItems().get(position));
        } else if (holder instanceof MainViewHolder.ListViewHolder){
            ((MainViewHolder.ListViewHolder) holder).bind(getItems().get(position));
        }
        holder.itemView.setOnClickListener(v -> getListener().onClick(v, getItems().get(position)));
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public List<PhotoData> getItems() {
        if (items == null) {
            items = new ArrayList<>(0);
        }
        return items;
    }

    public void setItems(List<PhotoData> items) {
        if (getItemCount() == 0) {
            this.items = items;
        } else {
            this.items.addAll(items);
        }
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
