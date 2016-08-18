package com.example.onlineshop;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productName;
    private String image;

    private String firstPrice;

    private final String secondPrice;

    public Product() {
        this.productName = "";
        this.image = "";
        this.firstPrice = "";
        this.secondPrice = "";
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(String secondPrice) {
        this.image = secondPrice;
    }

}