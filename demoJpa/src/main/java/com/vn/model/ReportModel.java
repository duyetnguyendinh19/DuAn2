package com.vn.model;

import java.io.Serializable;

public class ReportModel implements Serializable {
    public static final Long serialiable = 1L;

    private Long id;
    private String name;
    private String email;
    private String problem;
    private String mobile;
    private String opinion;
    private String reply;

    public ReportModel() {
    }

    public ReportModel(Long id, String name, String email, String problem, String mobile, String opinion, String reply) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.problem = problem;
        this.mobile = mobile;
        this.opinion = opinion;
        this.reply = reply;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
