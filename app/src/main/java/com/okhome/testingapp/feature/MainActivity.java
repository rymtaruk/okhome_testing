package com.okhome.testingapp.feature;

import com.okhome.testingapp.R;
import com.okhome.testingapp.base.BaseActivity;
import com.okhome.testingapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.okhome.testingapp.feature.MainAdapter.GRID_SPAN;
import static com.okhome.testingapp.feature.MainAdapter.LIST_SPAN;


public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private MainAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onSetup() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = getViewModelProvider().get(MainViewModel.class);
        viewModel.loadCurated();
    }

    @Override
    protected void onViewCreated() {
        setSupportActionBar(binding.toolbar);
        binding.toolbarLayout.setTitle("Awesome App");
        initRecyclerView();

        binding.viewContent.rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){

                    final int visibleThreshold = 2;

                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int lastItem  = 0;
                    int currentTotalCount = 0;
                    if (layoutManager != null) {
                        lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                        currentTotalCount = layoutManager.getItemCount();
                    }

                    if(currentTotalCount <= lastItem + visibleThreshold){
                        viewModel.loadMore();
                    }
                }
            }
        });
    }

    @Override
    protected void onObserverData() {
        viewModel.getPhotoData().observe(this, photoData -> {
            getAdapter().setItems(photoData);
            getAdapter().notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.act_view_list) {
            switchAdapterView(LIST_SPAN);
        } else {
            switchAdapterView(GRID_SPAN);
        }
        return true;
    }

    private void initRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, LIST_SPAN);
        getAdapter().setGridLayoutManager(gridLayoutManager);
        binding.viewContent.rvItems.setLayoutManager(gridLayoutManager);
        binding.viewContent.rvItems.setAdapter(getAdapter());
    }

    private void switchAdapterView(int span) {
        gridLayoutManager.setSpanCount(span);
        getAdapter().notifyItemRangeChanged(0, getAdapter().getItemCount());
    }

    private MainAdapter getAdapter() {
        if (adapter == null) {
            adapter = new MainAdapter();
        }
        return adapter;
    }
}