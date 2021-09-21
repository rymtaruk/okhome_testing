package com.okhome.testingapp.di.util;

import android.app.Application;
import android.content.Context;

import com.okhome.connection.constant.PropertiesData;
import com.okhome.connection.di.component.CoreComponent;
import com.okhome.connection.di.component.CoreComponentProvider;
import com.okhome.connection.di.component.DaggerCoreComponent;
import com.okhome.testingapp.BuildConfig;
import com.okhome.testingapp.di.component.AppComponent;
import com.okhome.testingapp.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class Injector implements CoreComponentProvider, HasAndroidInjector {
    private static Injector instance;
    private CoreComponent coreComponent;

    @Inject volatile DispatchingAndroidInjector<Object> androidInjector;

    public static Injector getInstance(Context context) {
        PropertiesData.getInstance().baseUrl = BuildConfig.BASE_URL;
        PropertiesData.getInstance().context = context;

        if (instance == null) {
            initialize((Application) context.getApplicationContext());
        }

        return instance;
    }

    public Injector(Application applicationContext) {
        AppComponent applicationComponent = DaggerAppComponent.builder()
                .application(applicationContext)
                .coreComponent(provideCoreComponent())
                .build();
        applicationComponent.inject(this);
    }

    public static void initialize(Application applicationContext) {
        instance = new Injector(applicationContext);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public CoreComponent provideCoreComponent() {
        if (coreComponent == null){
            coreComponent = DaggerCoreComponent.builder().build();
        }
        return coreComponent;
    }
}
