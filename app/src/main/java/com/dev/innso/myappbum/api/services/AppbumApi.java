package com.dev.innso.myappbum.api.services;

import com.dev.innso.myappbum.models.Appbum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppbumApi {

    @GET("/Appbum/getAppbum.php/{id}")
    Call<ArrayList<Appbum>> getUserAppbums(@Query("id") String id);
}
