package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_google")
@NamedQuery(name = "GmailGoogle.findAll", query = "SELECT u FROM GmailGoogle u")
public class GmailGoogle implements Serializable {

    private static final Long serializable = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "verified_email")
    private boolean verified_email;

    @Column(name = "name")
    private String name;

    @Column(name = "given_name")
    private String given_name;

    @Column(name = "family_name")
    private String family_name;

    @Column(name = "link")
    private String link;

    @Column(name = "picture")
    private String picture;

    public GmailGoogle() {
    }

    public GmailGoogle(String id, String email, boolean verified_email, String name, String given_name, String family_name, String link, String picture) {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.link = link;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
