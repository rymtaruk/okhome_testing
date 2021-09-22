package com.okhome.testingapp.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.okhome.connection.utils.ViewModelFactory;
import com.okhome.testingapp.di.util.Injector;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    ViewModelFactory viewModelFactory;
    private ViewModelProvider viewModelProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Injector.getInstance(this).androidInjector().inject(this);
        super.onCreate(savedInstanceState);
        onSetup();
        onViewCreated();
        onObserverData();
    }

    protected abstract void onSetup();
    protected abstract void onViewCreated();
    protected abstract void onObserverData();

    protected synchronized ViewModelProvider getViewModelProvider() {
        if (viewModelProvider == null) {
            viewModelProvider = new ViewModelProvider(this, viewModelFactory);
        }
        return viewModelProvider;
    }
}
