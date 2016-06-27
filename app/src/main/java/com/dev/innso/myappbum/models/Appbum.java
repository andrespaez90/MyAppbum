package com.dev.innso.myappbum.models;

import java.util.ArrayList;

public class Appbum {

    private int id;
    private String Name;
    private String Rol;
    private String urlCover;
    private Boolean isPrivate;
    private int passNumber;
    private String urlBackground;

    private int type;

    private ArrayList<Picture> Pictures;

    public Appbum(int id, String name, String rol, String urlCover, boolean isprivate, int passnumber, int type) {
        this.id = id;
        Name = name;
        Rol = rol;
        this. urlCover = urlCover;
        isPrivate = isprivate;
        passNumber = passnumber;
        this.type = type;
        Pictures = new ArrayList<Picture>();
    }

    public String getName() {
        return Name;
    }

    public String getUrlCover() {
        return  urlCover;
    }

    public void add(Picture photo) {
        this.Pictures.add(photo);
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPrivate(){ return isPrivate; }

    public int getPassNumber(){ return passNumber; }

    public int gettype() {
        return type;
    }

    public ArrayList<Picture> getPictures() {
        return Pictures;
    }
}
