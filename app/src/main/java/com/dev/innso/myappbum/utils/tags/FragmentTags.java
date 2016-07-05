package com.dev.innso.myappbum.utils.tags;


public enum FragmentTags {
    LIST_BUDDIES("ListBuddiesFragment"),
    CUSTOMIZE("CustomizeFragment"),
    ADDPICTURE("AddPictureFragment");

    private String text;

    FragmentTags(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
