package com.vn.model;

import com.vn.jpa.AuthUser;

import java.io.Serializable;
import java.util.Date;

public class InfomationModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long id;
    private String province;
    private String town;
    private String address;
    private String phone;
    private Date birthday;
    private String bank;
    private String company;
    private String atmNumberBank;
    private String infoPlus;
    private String isDelete;
    private AuthUser authUser;
    private String nameUser;
    private String emailUser;
    private String firstName;
    private String lastName;
    private String gender;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public InfomationModel() {
    }

    public InfomationModel(Long id, String province, String town, String address, String phone, Date birthday, String bank, String company, String atmNumberBank, String infoPlus, String isDelete, AuthUser authUser) {
        this.id = id;
        this.province = province;
        this.town = town;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.bank = bank;
        this.company = company;
        this.atmNumberBank = atmNumberBank;
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

    public String getAtmNumberBank() {
        return atmNumberBank;
    }

    public void setAtmNumberBank(String atmNumberBank) {
        this.atmNumberBank = atmNumberBank;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
    
    
}
