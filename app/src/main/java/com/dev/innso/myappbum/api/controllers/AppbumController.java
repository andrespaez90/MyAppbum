package com.dev.innso.myappbum.api.controllers;


import android.util.Log;

import com.dev.innso.myappbum.api.services.AppbumApi;
import com.dev.innso.myappbum.models.Appbum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppbumController {

    private ArrayList<Appbum> userAppbum;

    AppbumApi appbumApi;

    public AppbumController(AppbumApi appbumApi) {
        if (userAppbum == null) {
            this.appbumApi = appbumApi;
        }
    }

    public void getUserAppbums(String userId, AppbumResponse callback) {

        Call<ArrayList<Appbum>> appbumCall = appbumApi.getUserAppbums(userId);

        appbumCall.enqueue(new Callback<ArrayList<Appbum>>() {

            @Override
            public void onResponse(Call<ArrayList<Appbum>> call, Response<ArrayList<Appbum>> response) {

                if (response.isSuccessful()) {

                    userAppbum = response.body();

                    callback.success(userAppbum);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Appbum>> call, Throwable t) {
                Log.e("error", t.getMessage());
                callback.onFail(t.getMessage());
            }
        });
    }


    public interface AppbumResponse {

        void success(ArrayList<Appbum> appbumList);

        void onFail(String errorMessage);
    }
}
