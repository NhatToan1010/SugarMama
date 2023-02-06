package com.acer.sugarmama.model;

import java.io.Serializable;

public class TopProduct implements Serializable {
    private String name;
    private String price;
    private String rating;
    private String discount;
    private String description;
    private String imgUrl;
    private String type;

    public TopProduct() {
    }

    public TopProduct(String name, String price, String rating, String discount, String description, String imgUrl, String type) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.discount = discount;
        this.description = description;
        this.imgUrl = imgUrl;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getType() {
        return type;
    }
}
