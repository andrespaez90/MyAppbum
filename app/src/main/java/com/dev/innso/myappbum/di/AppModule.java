package com.dev.innso.myappbum.di;

import com.dev.innso.myappbum.app.AppbumApplication;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    ManagerPreferences managerPreferences() {
        ManagerPreferences.init(AppbumApplication.getAppContext());
        return ManagerPreferences.get();
    }
}
