package com.dev.innso.myappbum.Utils.TAGs;

/**
 * Created by INNSO SAS on 24/06/2015.
 */
public enum JSONTag {

    JSON_RESPONSE("result"),
    JSON_SUCCESS("Success"),

    JSON_USER_ID("id"),
    JSON_USER_IDFACE("idFacebook"),
    JSON_USER_NAME("name"),
    JSON_URLPROFILE("urlProfile"),
    JSON_URLCOVER("urlCover"),
    JSON_ARRAYPHOTOS("photos"),
    JSON_FACEBOOK_COVER("cover"),

    PHOTO_ID("id"),
    PHOTO_URL("urlImagen"),
    PHOTO_NAME("name"),
    PHOTO_TYPE("type"),

    JSON_USER_EMAIL("email"),
    JSON_USER_PASSWORD("pass");


    String text;

    private JSONTag(String text){ this.text = text; }

    @Override
    public String toString(){
        return  text;
    }
}
