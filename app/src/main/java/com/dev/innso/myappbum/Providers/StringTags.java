package com.dev.innso.myappbum.Providers;


public enum StringTags {

    ACTION_SUCCESS("ACTION_SUCCESS"),
    REGISTER_NAME("ERROR_NAME"),
    REGISTER_EMAIL("ERROR_EMAIL"),
    REGISTER_LENGHT_PASSWORD("LENGHT_PASSWORD"),
    REGISTER_PASSWORD("PASSWORD");

    private String text;

    private StringTags(String text){ this.text = text; }

    @Override
    public String toString(){
        return text;
    }
}
