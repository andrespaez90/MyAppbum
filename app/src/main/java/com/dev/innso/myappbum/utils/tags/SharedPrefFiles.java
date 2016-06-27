package com.dev.innso.myappbum.utils.tags;

/**
 * Created by jpardogo on 23/02/2014.
 */
public enum SharedPrefFiles {
    CUSTOMIZE_SETTINGS("CUSTOMIZE_SETTINGS");


    private String text;

    private SharedPrefFiles(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
