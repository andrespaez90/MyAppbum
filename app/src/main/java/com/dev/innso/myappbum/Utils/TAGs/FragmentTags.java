package com.dev.innso.myappbum.Utils.TAGs;


public enum FragmentTags {
    LIST_BUDDIES("ListBuddiesFragment"),
    CUSTOMIZE("CustomizeFragment"),
    ADDPICTURE("AddpictureFragment");

    private String text;

    private FragmentTags(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
