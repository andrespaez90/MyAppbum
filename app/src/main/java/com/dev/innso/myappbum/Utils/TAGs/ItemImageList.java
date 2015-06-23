package com.dev.innso.myappbum.Utils.TAGs;

/**
 * Created by INNSO SAS on 14/05/2015.
 */
public class ItemImageList {

    private String urlImg;
    private String nameAlbum;

    public ItemImageList(String name, String url){
        urlImg = url;
        nameAlbum = name;
    }

    public String getUrlImg(){
        return urlImg;
    }

    public String getNameAlbum(){
        return nameAlbum;
    }
}
