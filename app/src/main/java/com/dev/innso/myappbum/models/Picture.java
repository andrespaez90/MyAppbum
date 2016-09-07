package com.dev.innso.myappbum.models;

public class Picture {

    private String id;
    private String urlImagen;
    private String name;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlPicture() {
        return urlImagen;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlImagen = urlPicture;
    }

    public String getNamePicture() {
        return name;
    }

    public void setNamePicture(String namePicture) {
        this.name = namePicture;
    }

    public int getTypePicture() {
        return type;
    }

    public void setTypePicture(int typePicture) {
        this.type = typePicture;
    }


}
