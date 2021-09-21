package com.okhome.testingapp.feature;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.okhome.testingapp.R;
import com.okhome.testingapp.base.BaseActivity;
import com.okhome.testingapp.databinding.ActivityMainBinding;
import com.okhome.testingapp.di.util.Injector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;


public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onSetup() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = getViewModelProvider().get(MainViewModel.class);
        setContentView(binding.getRoot());
    }

    @Override
    protected void onViewCreated() {
        setSupportActionBar(binding.toolbar);
        binding.toolbarLayout.setTitle("Awesome App");
    }

    @Override
    protected void onObserverData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}