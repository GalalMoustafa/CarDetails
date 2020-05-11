package com.softexper.cardetails.Data.POJO;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    private int id;
    @SerializedName("brand")
    private String brand;
    @SerializedName("constructionYear")
    private String constractionYear;
    @SerializedName("isUsed")
    private String isUsed;
    @SerializedName("imageUrl")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getConstractionYear() {
        return constractionYear;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setConstractionYear(String constractionYear) {
        this.constractionYear = constractionYear;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
