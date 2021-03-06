package com.okhome.testingapp.di.module;

import com.okhome.testingapp.feature.MainActivity;
import com.okhome.testingapp.feature.detail.DetailInfoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract MainActivity injectMainActivity();

    @ContributesAndroidInjector
    abstract DetailInfoActivity injectDetailInfoActivity();
}
