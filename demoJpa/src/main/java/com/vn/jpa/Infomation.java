package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "info")
@NamedQuery(name = "Infomation.findAll", query = "SELECT i FROM Infomation i")
public class Infomation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province")
    private String province;

    @Column(name = "town")
    private String town;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "bank")
    private String bank;

    @Column(name = "company")
    private String company;

    @Column(name = "atm_number_bank")
    private String atmNumber;

    @Column(name = "info_plus")
    private String infoPlus;

    @Column(name = "isDelete", columnDefinition = "CHAR(1)")
    private String isDelete;

    @ManyToOne
    @JoinColumn(name = "id_auth_user")
    private AuthUser authUser;

    public Infomation() {
    }

    public Infomation(String province, String town, String address, String phone, Date birthday, String bank, String company, String atmNumber, String infoPlus, String isDelete, AuthUser authUser) {
        this.province = province;
        this.town = town;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.bank = bank;
        this.company = company;
        this.atmNumber = atmNumber;
        this.infoPlus = infoPlus;
        this.isDelete = isDelete;
        this.authUser = authUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAtmNumber() {
        return atmNumber;
    }

    public void setAtmNumber(String atmNumber) {
        this.atmNumber = atmNumber;
    }

    public String getInfoPlus() {
        return infoPlus;
    }

    public void setInfoPlus(String infoPlus) {
        this.infoPlus = infoPlus;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
