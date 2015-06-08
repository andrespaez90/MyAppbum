package com.dev.innso.myappbum.Providers;

/**
 * Created by INNSO SAS on 25/05/2015.
 */
public enum ActivityTags {

    ACTIVITY_REGISTER("Register");

    private String text;

    private ActivityTags(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}
