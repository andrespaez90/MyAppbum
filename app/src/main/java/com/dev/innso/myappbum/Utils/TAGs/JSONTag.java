package com.dev.innso.myappbum.Utils.TAGs;

/**
 * Created by INNSO SAS on 24/06/2015.
 */
public enum JSONTag {

    JSON_RESPONSE("result"),
    JSON_SUCCESS("Success"),

    USER_EMAIL("user"),
    USER_PASSWORD("pass");


    String text;

    private JSONTag(String text){ this.text = text; }

    @Override
    public String toString(){
        return  text;
    }
}
