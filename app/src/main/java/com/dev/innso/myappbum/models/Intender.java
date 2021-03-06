package com.dev.innso.myappbum.models;

import android.content.Context;
import android.content.Intent;

import com.dev.innso.myappbum.providers.BuddiesImages;
import com.dev.innso.myappbum.ui.BuddiesActivity;

import java.util.ArrayList;

public class Intender {

    public static Intent createIntent(Appbum appbum, Context mContext){
        Intent intent = null;
        if( appbum.gettype() == 1 ){
            intent = new Intent(mContext, BuddiesActivity.class);
            intent.putExtra("APPBUM_NAME", appbum.getName());
            putBuddiesImages(appbum);
        }
        return intent;
    }

    private static void putBuddiesImages(Appbum appbum){
        BuddiesImages.imageUrls_left = new ArrayList<>();
        BuddiesImages.imageUrls_right = new ArrayList<>();

        for(Picture p : appbum.getPhotos()){
            if(p.getTypePicture() == 1){
                BuddiesImages.imageUrls_left.add(p.getUrlPicture());
            }else{
                BuddiesImages.imageUrls_right.add(p.getUrlPicture());
            }
        }
    }
}
