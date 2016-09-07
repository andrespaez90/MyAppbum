package com.dev.innso.myappbum.di.component;

import com.dev.innso.myappbum.api.services.AppbumApi;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.di.AppModule;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;
import com.dev.innso.myappbum.ui.LoginActivity;
import com.dev.innso.myappbum.ui.RegisterActivity;
import com.dev.innso.myappbum.ui.activities.MainActivity;
import com.dev.innso.myappbum.ui.activities.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApiModule.class, AppModule.class})
public interface AppComponent {

    Retrofit retrofit();

    AppbumApi appbumApi();

    ManagerPreferences managerPreference();

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(RegisterActivity registerActivity);

    void inject(LoginActivity loginActivity);
}
