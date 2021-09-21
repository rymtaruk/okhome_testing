package com.okhome.testingapp.di.module;

import com.okhome.testingapp.api.Api;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    Api provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
