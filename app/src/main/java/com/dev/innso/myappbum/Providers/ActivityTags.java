package com.dev.innso.myappbum.Providers;

/**
 * Created by INNSO SAS on 25/05/2015.
 */
public enum ActivityTags {

    ACTIVITY_START("START"),
    ACTIVITY_LOGIN("LOGIN"),
    ACTIVITY_REGISTER("REGISTER");

    private String text;

    private ActivityTags(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}
