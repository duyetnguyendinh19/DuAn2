package com.vn.model;

import com.vn.jpa.AuthUser;

import java.io.Serializable;
import java.util.Date;

public class BillModel implements Serializable {
    public static final Long serialize = 1L;

    private Long id;
    private Float total;
    private Integer payment;
    private Integer status;
    private Date createDate;
    private String createdBy;
    private String isdelete;
    private String address;
    private String name;
    private String email;
    private AuthUser authUser;
    private String code;
    private String bankCode;


    public BillModel() {
    }

    public BillModel(Long id, Float total, Integer payment, Integer status, Date createDate, String createdBy, String isdelete, String address, String name, String email, AuthUser authUser, String code) {
        this.id = id;
        this.total = total;
        this.payment = payment;
        this.status = status;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.isdelete = isdelete;
        this.address = address;
        this.name = name;
        this.email = email;
        this.authUser = authUser;
        this.code = code;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
