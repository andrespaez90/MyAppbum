package com.dev.innso.myappbum.utils.tags;

public enum ActivityTags {

    ACTIVITY_START("START",1),
    ACTIVITY_LOGIN("LOGIN",2),
    ACTIVITY_REGISTER("REGISTER",3),
    ACTIVITY_PASSNUMBER("PASSNUMBER",4);

    private String text;
    private int code;

    private ActivityTags(String text,int code) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getCode(){ return code; }

}
