package com.vn.model;

import com.vn.jpa.Product;

import java.io.Serializable;
import java.util.Date;

public class ReviewModel implements Serializable {
    public static final Long serializable = 1L;

    private Long id;
    private String name;
    private Date createdDate;
    private String description;
    private String reply;
    private Integer status;
    private Integer rate;
    private Product product;

    public ReviewModel() {
    }

    public ReviewModel(Long id, String name, Date createdDate, String description, String reply, Integer status, Integer rate, Product product) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.description = description;
        this.reply = reply;
        this.status = status;
        this.rate = rate;
        this.product = product;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
