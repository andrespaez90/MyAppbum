package com.dev.innso.myappbum.di.component;

import com.dev.innso.myappbum.api.services.AppbumApi;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.ui.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    Retrofit retrofit();

    AppbumApi appbumApi();

    void inject(MainActivity mainActivity);
}
