package com.okhome.connection.di.component;

import com.okhome.connection.di.module.ProviderModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ProviderModule.class})
public interface CoreComponent {
    Retrofit provideRetrofit();
}
