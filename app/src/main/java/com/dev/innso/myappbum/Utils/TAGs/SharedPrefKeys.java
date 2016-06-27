package com.dev.innso.myappbum.Utils.TAGs;

/**
 * Created by jpardogo on 23/02/2014.
 */
public enum SharedPrefKeys {

    GAP_PROGRESS("GAP_PROGRESS"),
    SPEED_PROGRESS("SPEED_PROGRESS"),
    DIV_HEIGHT_PROGRESS("DIV_HEIGHT_PROGRESS"),
    FILL_GAP_POSITION("FILL_GAP_POSITION"),
    MANUAL_SCROLL_POSITION("MANUAL_SCROLL_POSITION"),
    AUTO_SCROLL_POSITION("AUTO_SCROLL_POSITION"),
    DIVIDERS_POSITION("DIVIDERS_POSITION"),

    ACCESS_TOKEN("ACCESS_TOKEN"),
    FACEBOOK_USERID("FACEBOOK_USERID"),

    COVER_USER("COVER_USER"),
    NAME_USER("NAME_USER"),
    EMAIL_USER("EMAIL_USER"),
    USER_ID("USER_ID"),
    PROFILE_USER("PROFILE_USER");

    private String text;

    SharedPrefKeys(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
