package com.vn.model;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UserAccountSearchingForm implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    @Pattern(regexp = "(^$)|(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)", message = "Không đúng định dạng email.")
    private String email;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAccountSearchingForm(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public UserAccountSearchingForm() {
    }
}
