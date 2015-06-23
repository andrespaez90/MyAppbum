package com.dev.innso.myappbum.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by INNSO SAS on 23/06/2015.
 */
public class FactoryModel {

    public static void createAppbums(JSONArray jsonArray) {
        JSONObject object;
        Appbum appbum;
        try {
            for(int i = 0; i < jsonArray.length();i++){
                object = jsonArray.getJSONObject(i);
                appbum = new Appbum(object.getInt("id"),object.getString("name"),object.getString("rol"),object.getString("urlCover") );
                FacadeModel.Appbums.add(appbum);
            }
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
