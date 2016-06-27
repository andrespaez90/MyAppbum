package com.dev.innso.myappbum.utils;

public enum ExtraArgumentKeys {
    OPEN_ACTIVITES("OPEN_ACTIVITES");

    private String text;

    private ExtraArgumentKeys(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}