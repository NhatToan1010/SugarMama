package com.acer.sugarmama.model;

public class Flavor {
    private String name;
    private String imgUrl;

    public Flavor() {
    }

    public Flavor(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
