package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "price_sale")
    private float priceSale;

    @Column(name = "status")
    private int status;

    @Column(name = "mainImg" )
    private String mainImg;

    @Column(name = "subImg", columnDefinition = "TEXT")
    private String subImg;

    @Column(name = "isDelete", columnDefinition = "CHAR(1)")
    private String isdelete;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date")
//    private Date createdDate;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Product_Bill> lsProduct_bills = new ArrayList<>();
    
    public Product() {
    }

    public Product(String name, float price, int quantity, String description, float priceSale, int status, String mainImg, String subImg, String isdelete, String info, Category category, List<Product_Bill> lsProduct_bills) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.priceSale = priceSale;
        this.status = status;
        this.mainImg = mainImg;
        this.subImg = subImg;
        this.isdelete = isdelete;
        this.info = info;
        this.category = category;
        this.lsProduct_bills = lsProduct_bills;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(float priceSale) {
        this.priceSale = priceSale;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getSubImg() {
        return subImg;
    }

    public void setSubImg(String subImg) {
        this.subImg = subImg;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product_Bill> getLsProduct_bills() {
        return lsProduct_bills;
    }

    public void setLsProduct_bills(List<Product_Bill> lsProduct_bills) {
        this.lsProduct_bills = lsProduct_bills;
    }
}

