package com.vn.model;

import java.util.Date;

public class ProductBillModel {

    private Long id;
    private String mainImg;
    private String nameProduct;
    private Float priceProduct;
    private Float priceSaleProduct;
    private Integer quantity;
    private String nameBill;
    private String codeBill;
    private String emailBill;
    private String addressBill;
    private String mobileBill;
    private Date createdBill;
    private Integer statusBill;
    private Integer paymentBill;

    public ProductBillModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Float getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Float priceProduct) {
        this.priceProduct = priceProduct;
    }

    public Float getPriceSaleProduct() {
        return priceSaleProduct;
    }

    public void setPriceSaleProduct(Float priceSaleProduct) {
        this.priceSaleProduct = priceSaleProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNameBill() {
        return nameBill;
    }

    public void setNameBill(String nameBill) {
        this.nameBill = nameBill;
    }

    public String getCodeBill() {
        return codeBill;
    }

    public void setCodeBill(String codeBill) {
        this.codeBill = codeBill;
    }

    public String getEmailBill() {
        return emailBill;
    }

    public void setEmailBill(String emailBill) {
        this.emailBill = emailBill;
    }

    public String getAddressBill() {
        return addressBill;
    }

    public void setAddressBill(String addressBill) {
        this.addressBill = addressBill;
    }

    public String getMobileBill() {
        return mobileBill;
    }

    public void setMobileBill(String mobileBill) {
        this.mobileBill = mobileBill;
    }

    public Date getCreatedBill() {
        return createdBill;
    }

    public void setCreatedBill(Date createdBill) {
        this.createdBill = createdBill;
    }

    public Integer getStatusBill() {
        return statusBill;
    }

    public void setStatusBill(Integer statusBill) {
        this.statusBill = statusBill;
    }

    public Integer getPaymentBill() {
        return paymentBill;
    }

    public void setPaymentBill(Integer paymentBill) {
        this.paymentBill = paymentBill;
    }
}
