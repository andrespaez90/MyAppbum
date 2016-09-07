package com.dev.innso.myappbum.di;

import com.dev.innso.myappbum.api.services.AppbumApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    AppbumApi appbumApi(Retrofit retrofit) {
        return retrofit.create(AppbumApi.class);
    }


    @Provides
    @Singleton
    public Retrofit retrofit(Gson gson) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("http://www.andrespaez90.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    public Gson gson() {
        return new GsonBuilder()
                .create();
    }

}
