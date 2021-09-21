package com.okhome.testingapp.di.component;

import android.app.Application;

import com.okhome.connection.annotation.AppScope;
import com.okhome.connection.di.component.CoreComponent;
import com.okhome.testingapp.di.module.ActivityBindingModule;
import com.okhome.testingapp.di.module.ApiModule;
import com.okhome.testingapp.di.module.ContextModule;
import com.okhome.testingapp.di.module.ViewModelBindingModule;
import com.okhome.testingapp.di.util.Injector;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {ContextModule.class, ApiModule.class, ViewModelBindingModule.class,  AndroidInjectionModule.class, ActivityBindingModule.class}, dependencies = {CoreComponent.class})
@AppScope
public interface AppComponent extends AndroidInjector<Injector>{

    void inject(Injector injector);

    @Component.Builder
    interface Build{
        @BindsInstance
        Build application(Application application);
        Build coreComponent(CoreComponent coreComponent);
        AppComponent build();
    }
}
