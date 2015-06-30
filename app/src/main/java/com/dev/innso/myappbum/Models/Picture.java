package com.dev.innso.myappbum.Models;

/**
 * Created by INNSO SAS on 30/06/2015.
 */
public class Picture {

    private String id;
    private String urlPicture;
    private String namePicture;
    private int typePicture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getNamePicture() {
        return namePicture;
    }

    public void setNamePicture(String namePicture) {
        this.namePicture = namePicture;
    }

    public int getTypePicture() {
        return typePicture;
    }

    public void setTypePicture(int typePicture) {
        this.typePicture = typePicture;
    }


}
