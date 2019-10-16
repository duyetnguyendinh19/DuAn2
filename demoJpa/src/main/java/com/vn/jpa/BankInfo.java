package com.vn.jpa;

import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bank_info")
@NamedQuery(name = "BankInfo.findAll",query = "SELECT b FROM BankInfo b")
public class BankInfo implements Serializable {

    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_code")
    private String code;

    @Column(name = "bank_name")
    private String name;

    @Column(name = "bank_icon")
    private String icon;

    @Column(name = "bank_type")
    private int type;

    @Column(name = "bank_info", columnDefinition = "TEXT")
    private String info;

    public BankInfo() {
    }

    public BankInfo(String code, String name, String icon, int type, String info) {
        this.code = code;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
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
}
