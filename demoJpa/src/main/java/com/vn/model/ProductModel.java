package com.vn.model;


import com.vn.common.FileUtils.DescriptionBase64File;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

public class ProductModel implements Serializable {
    public static final Long serializable = 1L;

    private Long id;
    private String name;
    private Float price;
    private Integer quantity;
    private String description;
    private Float priceSale;
    private Integer status;
    private String removeSub;
    private DescriptionBase64File mainImg;
    private DescriptionBase64File[] subImg;
    private String isDelete;
    private String info;
    private Integer rate;
    private Long idCate;

    public ProductModel() {
    }

    public ProductModel(Long id, String name, Float price, Integer quantity, String description, Float priceSale, Integer status, DescriptionBase64File mainImg, DescriptionBase64File[] subImg, String isDelete, String info, Integer rate, Long idCate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.priceSale = priceSale;
        this.status = status;
        this.mainImg = mainImg;
        this.subImg = subImg;
        this.isDelete = isDelete;
        this.info = info;
        this.rate = rate;
        this.idCate = idCate;
    }

    public String getRemoveSub() {
        return removeSub;
    }

    public void setRemoveSub(String removeSub) {
        this.removeSub = removeSub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Float priceSale) {
        this.priceSale = priceSale;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DescriptionBase64File getMainImg() {
        return mainImg;
    }

    public void setMainImg(DescriptionBase64File mainImg) {
        this.mainImg = mainImg;
    }

    public DescriptionBase64File[] getSubImg() {
        return subImg;
    }

    public void setSubImg(DescriptionBase64File[] subImg) {
        this.subImg = subImg;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getIdCate() {
        return idCate;
    }

    public void setIdCate(Long idCate) {
        this.idCate = idCate;
    }
}
