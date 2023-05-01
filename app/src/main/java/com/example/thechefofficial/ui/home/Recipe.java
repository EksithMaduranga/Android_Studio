package com.example.thechefofficial.ui.home;

public class Recipe {



    String id;
    String imgId;
    String postImg;
    String postName;
    String postdes;

    public Recipe(String id, String imgId, String postImg, String postName, String postdes) {
        this.id = id;
        this.imgId = imgId;
        this.postImg = postImg;
        this.postName = postName;
        this.postdes = postdes;
    }

    public Recipe() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostdes() {
        return postdes;
    }

    public void setPostdes(String postdes) {
        this.postdes = postdes;
    }


}
