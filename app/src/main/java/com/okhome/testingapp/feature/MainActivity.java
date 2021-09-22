package com.okhome.testingapp.feature;

import com.okhome.connection.utils.RecyclerViewListener;
import com.okhome.testingapp.R;
import com.okhome.testingapp.base.BaseActivity;
import com.okhome.testingapp.databinding.ActivityMainBinding;
import com.okhome.testingapp.feature.detail.DetailInfoActivity;
import com.okhome.testingapp.model.PhotoData;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.okhome.testingapp.feature.MainAdapter.GRID_SPAN;
import static com.okhome.testingapp.feature.MainAdapter.LIST_SPAN;
import static com.okhome.testingapp.feature.detail.DetailInfoActivity.PHOTO_ID;


public class MainActivity extends BaseActivity implements RecyclerViewListener<PhotoData>, SwipeRefreshLayout.OnRefreshListener {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private MainAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private boolean isLoad = false;

    @Override
    protected void onSetup() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = getViewModelProvider().get(MainViewModel.class);
    }

    @Override
    protected void onViewCreated() {
        setSupportActionBar(binding.toolbar);
        binding.toolbarLayout.setTitle("Awesome App");
        binding.viewContent.getRoot().setOnRefreshListener(this);
        binding.fabScrollDown.setOnClickListener(v -> scrollDown());
        if (checkNetwork()){
            viewModel.loadCurated();
        }
        initRecyclerView();

        binding.viewContent.nScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                    if (isLoad){
                        viewModel.loadMore();
                    }
                }
            }
        });
    }

    @Override
    protected void onObserverData() {
        viewModel.getErrorMessage().observe(this, errorMessage -> Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show());

        viewModel.getLoadingState().observe(this, status -> {
            binding.viewContent.getRoot().setRefreshing(false);
            dismissLoadingMore();
            if (status) {
                binding.viewContent.sflLoading.startShimmer();
                binding.viewContent.sflLoading.setVisibility(View.VISIBLE);
                binding.viewContent.rvItems.setVisibility(View.GONE);
            } else {
                binding.viewContent.sflLoading.stopShimmer();
                binding.viewContent.sflLoading.setVisibility(View.GONE);
                binding.viewContent.rvItems.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getLoadingMore().observe(this, status -> {
            if (status){
                showLoadingMore();
                dismissButtonScroll();
            } else {
                dismissLoadingMore();
                showButtonScroll();
            }
        });

        viewModel.getPhotoData().observe(this, photoData -> {
            isLoad = true;
            getAdapter().setItems(photoData);
            getAdapter().notifyDataSetChanged();
        });

        viewModel.getDataOnRefresh().observe(this, photoData -> {
            getAdapter().refreshList();
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
        getAdapter().setListener(this);
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

    @Override
    public void onClick(View view, PhotoData data) {
        Intent intent = new Intent(view.getContext(), DetailInfoActivity.class);
        intent.putExtra(PHOTO_ID, data.getId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (checkNetwork()){
            viewModel.loadOnRefresh();
        }
    }

    private boolean checkNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            binding.viewContent.tvRefreshInfo.setVisibility(View.GONE);
            return true;
        } else {
            binding.viewContent.sflLoading.setVisibility(View.GONE);
            binding.viewContent.getRoot().setRefreshing(false);
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            binding.viewContent.tvRefreshInfo.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private void showLoadingMore(){
        binding.viewContent.pbLoading.setVisibility(View.VISIBLE);
        binding.viewContent.pbLoading.setIndeterminate(true);
        scrollDown();
    }

    private void dismissLoadingMore(){
        binding.viewContent.pbLoading.setVisibility(View.GONE);
        binding.viewContent.pbLoading.setIndeterminate(false);
    }

    private void scrollDown(){
        binding.viewContent.nScrollView.post(() -> binding.viewContent.nScrollView.fullScroll(View.FOCUS_DOWN));
    }

    private void showButtonScroll(){
        binding.fabScrollDown.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.show_down_to_up);
        binding.fabScrollDown.setAnimation(anim);
    }

    private void dismissButtonScroll(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.dismiss_up_to_down);
        binding.fabScrollDown.setAnimation(anim);
        binding.fabScrollDown.setVisibility(View.GONE);
    }
}