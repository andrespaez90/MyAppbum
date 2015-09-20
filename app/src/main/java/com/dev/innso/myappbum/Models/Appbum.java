package com.dev.innso.myappbum.Models;

import java.util.ArrayList;

/**
 * Created by INNSO SAS on 23/06/2015.
 */
public class Appbum {

    private int id;
    private String Name;
    private String Rol;
    private String urlCover;
    private String urlBackground;

    private int type;

    private ArrayList<Picture> Pictures;

    public Appbum(int id, String name, String rol, String urlCover, int type) {
        this.id = id;
        Name = name;
        Rol = rol;
        this. urlCover = urlCover;
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

    public int gettype() {
        return type;
    }

    public ArrayList<Picture> getPictures() {
        return Pictures;
    }
}
