package com.dev.innso.myappbum.Models;

import com.dev.innso.myappbum.Utils.TAGs.JSONTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by INNSO SAS on 23/06/2015.
 */
public class FactoryModel {

    public static void createAppbums(JSONArray jsonArray) {
        FacadeModel.Appbums = new ArrayList<>();
        JSONObject object;
        JSONObject jsonPhoto;
        Appbum appbum;
        Picture photoApp;
        try {
            for(int i = 0; i < jsonArray.length();i++){
                object = jsonArray.getJSONObject(i);
                appbum = new Appbum(object.getInt("id"),object.getString("name"),object.getString("rol"),object.getString("urlCover"), object.getInt(JSONTag.PHOTO_TYPE.toString()) );
                JSONArray arrayPhotos = object.getJSONArray(JSONTag.JSON_ARRAYPHOTOS.toString());
                for(int j=0; j<arrayPhotos.length();j++ ){
                    jsonPhoto = arrayPhotos.getJSONObject(j);
                    photoApp = new Picture();
                    photoApp.setId(jsonPhoto.getString(JSONTag.PHOTO_ID.toString()));
                    photoApp.setUrlPicture(jsonPhoto.getString(JSONTag.PHOTO_URL.toString()));
                    photoApp.setTypePicture(jsonPhoto.getInt(JSONTag.PHOTO_TYPE.toString()));
                    photoApp.setNamePicture(jsonPhoto.getString(JSONTag.PHOTO_NAME.toString()));

                    appbum.add(photoApp);
                }

                FacadeModel.Appbums.add(appbum);
            }
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
