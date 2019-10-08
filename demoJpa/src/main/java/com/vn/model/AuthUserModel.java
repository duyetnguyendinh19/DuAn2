package com.vn.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AuthUserModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date createdDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String gender;
    private byte status;
    private byte userType;
    private byte isVerified;
    private String userName;
    private String password;
    private String verifyPassword;
    private String email;
    private List<Long> roles;
    private String lsRoles;

    public String getLsRoles() {
        return this.lsRoles;
    }

    public void setLsRoles(String lsRoles) {
        this.lsRoles = lsRoles;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte getStatus() {
        return this.status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getUserType() {
        return this.userType;
    }

    public void setUserType(byte userType) {
        this.userType = userType;
    }

    public byte getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(byte isVerified) {
        this.isVerified = isVerified;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return this.verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AuthUserModel(Long id, String userName, String fullName, String email, byte status, byte userType, Date createdDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.fullName = fullName;
        this.status = status;
        this.userType = userType;
        this.userName = userName;
        this.email = email;
    }

    public AuthUserModel(Long id, String userName, String fullName, String email, byte status,  Date createdDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.fullName = fullName;
        this.status = status;
        this.userName = userName;
        this.email = email;
    }


    public AuthUserModel() {
    }
}
