package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reject")
@NamedQuery(name = "Reject.findAll",query = "SELECT r FROM Reject r")
public class Reject implements Serializable {

    private static final Long serizlizable = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reason")
    private String reason;

    @Column(name = "code_bill")
    private String code;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    public Reject() {
    }

    public Reject(String name, String reason, String code, String email, String mobile, String address, Date createdDate) {
        this.name = name;
        this.reason = reason;
        this.code = code;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.createdDate = createdDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @PrePersist
    public void pre(){
        if(this.createdDate == null){
            this.createdDate = new Date();
        }
    }
}
