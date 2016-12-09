package com.dev.innso.myappbum.app;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

public class AppbumApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static Context getAppContext() {
        return mContext;
    }
}
