package com.okhome.testingapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.okhome.connection.annotation.ViewModelKey;
import com.okhome.connection.utils.ViewModelFactory;
import com.okhome.testingapp.feature.MainViewModel;
import com.okhome.testingapp.feature.detail.DetailInfoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailInfoViewModel.class)
    abstract ViewModel bindDetailInfoViewModel(DetailInfoViewModel detailInfoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
