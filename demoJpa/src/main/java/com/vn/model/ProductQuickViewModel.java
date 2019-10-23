package com.vn.model;

public class ProductQuickViewModel {

    private String name;
    private Float price;
    private Float priceSale;
    private String mainImg;
    private String description;
    private int status;
    private String info;
    private Integer quantity;

    public ProductQuickViewModel() {
    }

    public ProductQuickViewModel(String name, Float price, Float priceSale, String mainImg, String description, int status) {
        this.name = name;
        this.price = price;
        this.priceSale = priceSale;
        this.mainImg = mainImg;
        this.description = description;
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Float priceSale) {
        this.priceSale = priceSale;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
