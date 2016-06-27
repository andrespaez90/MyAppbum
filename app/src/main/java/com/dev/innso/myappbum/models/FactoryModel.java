package com.dev.innso.myappbum.models;

import com.dev.innso.myappbum.utils.tags.JSONTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FactoryModel {

    private final static String sAppbumId = "id";
    private final static String sAppbumName = "name";
    private final static String sAppbumRol = "rol";
    private final static String sAppbumCover = "urlCover";
    private final static String sAppbumPrivacity = "private";
    private final static String sAppbumPassNumber = "passnumber";

    public static void createAppbums(JSONArray jsonArray) {
        FacadeModel.Appbums.clear();
        JSONObject object;
        JSONObject jsonPhoto;
        Appbum appbum;
        Picture photoApp;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                object = jsonArray.getJSONObject(i);

                appbum = new Appbum(object.getInt(sAppbumId), object.getString(sAppbumName), object.getString(sAppbumRol),
                        object.getString(sAppbumCover), object.getInt(sAppbumPrivacity) == 1,
                        object.getInt(sAppbumPassNumber), object.getInt(JSONTag.PHOTO_TYPE.toString()));

                JSONArray arrayPhotos = object.getJSONArray(JSONTag.JSON_ARRAYPHOTOS.toString());
                for (int j = 0; j < arrayPhotos.length(); j++) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
