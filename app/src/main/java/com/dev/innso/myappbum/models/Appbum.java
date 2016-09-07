package com.dev.innso.myappbum.models;

import java.util.ArrayList;

public class Appbum {

    private int id;
    private String name;
    private String rol;
    private String urlCover;
    private int isPrivate;
    private int passNumber;
    private String urlBackground;

    private int type;

    private ArrayList<Picture> photos;

    public Appbum(int id, String name, String rol, String urlCover, int isprivate, int passnumber, int type) {
        this.id = id;
        this.name = name;
        this.rol = rol;
        this. urlCover = urlCover;
        isPrivate = isprivate;
        passNumber = passnumber;
        this.type = type;
        photos = new ArrayList<Picture>();
    }

    public String getName() {
        return name;
    }

    public String getUrlCover() {
        return  urlCover;
    }

    public void add(Picture photo) {
        this.photos.add(photo);
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPrivate(){ return isPrivate == 1; }

    public int getPassNumber(){ return passNumber; }

    public int gettype() {
        return type;
    }

    public ArrayList<Picture> getPhotos() {
        return photos;
    }
}
