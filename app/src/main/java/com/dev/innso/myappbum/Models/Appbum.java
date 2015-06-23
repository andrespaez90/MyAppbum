package com.dev.innso.myappbum.Models;

import com.dev.innso.myappbum.Models.Appbums.Photo;

import java.util.ArrayList;

/**
 * Created by INNSO SAS on 23/06/2015.
 */
public class Appbum {

    private int id;
    private String Name;
    private String Rol;
    private String urlCover;

    private ArrayList<Photo> Photos;

    public Appbum(int id, String name, String rol, String urlCover) {
        this.id = id;
        Name = name;
        Rol = rol;
        this.urlCover = urlCover;
        Photos = new ArrayList<Photo>();
    }

    public String getName() {
        return Name;
    }

    public String getUrlCover() {
        return urlCover;
    }
}
