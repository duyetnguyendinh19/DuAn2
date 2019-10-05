package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "review")
@NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
public class Review implements Serializable {

    public static final Long serializable = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createDate;

    @Column(name = "description")
    private String description;

    @Column(name = "reply")
    private String reply;

    @Column(name = "rate", columnDefinition = "TINYINT")
    private Integer rate;

    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    public Review() {
    }

    public Review(String name, Date createDate, String description, String reply, Integer rate, int status, Product product) {
        this.name = name;
        this.createDate = createDate;
        this.description = description;
        this.reply = reply;
        this.rate = rate;
        this.status = status;
        this.product = product;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @PrePersist
    public void prePersist() {
        if (this.createDate == null) {
            this.createDate = new Date();
        }
    }

    public static enum status {

        INACTIVE(0), ACTIVE(1);
        private final int value;

        private status(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
