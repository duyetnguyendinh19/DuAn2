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
    private AuthUser authUser;

    public BillModel() {
    }

    public BillModel(Long id, Float total, Integer payment, Integer status, Date createDate, String createdBy, String isdelete, AuthUser authUser) {
        this.id = id;
        this.total = total;
        this.payment = payment;
        this.status = status;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.isdelete = isdelete;
        this.authUser = authUser;
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
