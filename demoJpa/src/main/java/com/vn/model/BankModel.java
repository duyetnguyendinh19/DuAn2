package com.vn.model;

import java.io.Serializable;

public class BankModel implements Serializable {
    public static final Long serializable = 1L;

    private Long id;
    private String code;
    private String name;
    private String icon;
    private Integer type;
    private String info;

    public BankModel() {
    }

    public BankModel(Long id, String code, String name, String icon, Integer type, String info) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

