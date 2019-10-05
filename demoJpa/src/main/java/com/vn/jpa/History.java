package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "history")
@NamedQuery(name = "History.findAll", query = "SELECT h FROM History h")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status", columnDefinition = "TINYINT(4)")
    private int status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createDate;

    @Column(name = "created_by")
    private String createBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifyDate;

    @Column(name = "modified_by")
    private String modifyBy;

    public History() {
    }

    public History(String description, int status, Date createDate, String createBy, Date modifyDate, String modifyBy) {
        this.description = description;
        this.status = status;
        this.createDate = createDate;
        this.createBy = createBy;
        this.modifyDate = modifyDate;
        this.modifyBy = modifyBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    @PrePersist
    public void prePersist(){
        if(this.createDate == null){
            this.createDate = new Date();
        }
        if(this.modifyDate == null){
            this.modifyDate = new Date();
        }
    }
}
