package com.okhome.connection.di.module;

import com.okhome.connection.constant.PropertiesData;
import com.okhome.connection.retrofit.HttpConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ProviderModule {

    @Singleton
    @Provides
    Retrofit providerRetrofit(){
        OkHttpClient.Builder client = HttpConfiguration.getClient();
        return new Retrofit.Builder()
                .baseUrl(PropertiesData.getInstance().baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }
}
