package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

    public static final Long serialize = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "status")
    private int status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date date;

    @Column(name = "isdelete", columnDefinition = "CHAR(1)")
    private String isDelete;

    public Category() {
    }

    public Category(String name, Long parentId, int status, Date date, String isDelete) {
        this.name = name;
        this.parentId = parentId;
        this.status = status;
        this.date = date;
        this.isDelete = isDelete;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @PrePersist
    public void prePersist(){
        if(this.date == null){
            this.date = new Date();
        }
    }

    public static enum status{
        ACTIVE(0), INACTIVE(1);

        private final int value;

        private status(int value) {
            this.value = value;
        }

        public int value(){
            return this.value;
        }
    }

    public static enum typeId{
        PARENT(0L), CHILDREN(1L);

        private final Long value;

        private typeId(Long value) {
            this.value = value;
        }

        public Long value(){
            return this.value;
        }
    }
}
