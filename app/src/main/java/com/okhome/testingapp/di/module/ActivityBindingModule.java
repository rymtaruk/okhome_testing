package com.okhome.testingapp.di.module;

import com.okhome.testingapp.feature.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
