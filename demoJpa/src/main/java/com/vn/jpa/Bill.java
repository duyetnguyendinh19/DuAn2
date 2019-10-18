package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bill")
@NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Float total;

    @Column(name = "payment", columnDefinition = "TINYINT")
    private int payment;

    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createDate;

    @Column(name = "created_by")
    private String createBy;

    @Column(name = "isDelete", columnDefinition = "CHAR")
    private String isDelete;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "bill")
    private List<Product_Bill> product_bills = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_auth_user")
    private AuthUser authUser;


    public Bill() {
    }

    public Bill(Float total, int payment, int status, Date createDate, String createBy, String isDelete, String address, String name, String email, String mobile, String code, List<Product_Bill> product_bills, AuthUser authUser) {
        this.total = total;
        this.payment = payment;
        this.status = status;
        this.createDate = createDate;
        this.createBy = createBy;
        this.isDelete = isDelete;
        this.address = address;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.code = code;
        this.product_bills = product_bills;
        this.authUser = authUser;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public List<Product_Bill> getProduct_bills() {
        return product_bills;
    }

    public void setProduct_bills(List<Product_Bill> product_bills) {
        this.product_bills = product_bills;
    }

    @PrePersist
    public void prePersist(){
        if(this.createDate == null){
            this.createDate = new Date();
        }
    }

    public static enum payment{
        LIVE(0), ONLINE(1);
        private final Integer value;
		private payment(Integer value) {
            this.value = value;
        }
        public Integer value() {
            return this.value;
        }
    }

}
