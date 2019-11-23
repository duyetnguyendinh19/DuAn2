package com.vn.model;

import java.util.Date;

public class BillProfileModel {

    private Date createDate;
    private String code;
    private Float total;
    private Integer typeStatus;
    private Integer status;

    public BillProfileModel() {
    }

    public BillProfileModel(Date createdDate, String code, Float total, Integer typeStatus, Integer status) {
        this.createDate = createdDate;
        this.code = code;
        this.total = total;
        this.typeStatus = typeStatus;
        this.status = status;
    }

    public Date getCreatedDate() {
        return createDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createDate = createdDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(Integer typeStatus) {
        this.typeStatus = typeStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
