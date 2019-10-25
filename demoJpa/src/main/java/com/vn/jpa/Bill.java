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

    @Column(name = "type_status", columnDefinition = "TINYINT")
    private Integer typeStatus;

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

    //    @OneToMany(mappedBy = "bill")
//    private List<Product_Bill> product_bills = new ArrayList<>();
    @Column(name = "mail_status", columnDefinition = "TINYINT")
    private Integer mailStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_auth_user")
    private AuthUser authUser;


    public Bill() {
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
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

//    public List<Product_Bill> getProduct_bills() {
//        return product_bills;
//    }
//
//    public void setProduct_bills(List<Product_Bill> product_bills) {
//        this.product_bills = product_bills;
//    }

    public Integer getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(Integer typeStatus) {
        this.typeStatus = typeStatus;
    }

    @PrePersist
    public void prePersist() {
        if (this.createDate == null) {
            this.createDate = new Date();
        }
    }

    public static enum payment {
        LIVE(0), ONLINE(1);
        private final Integer value;

        private payment(Integer value) {
            this.value = value;
        }

        public Integer value() {
            return this.value;
        }
    }

    public static enum STATUSPAYMENT {
        PAID(0) /*KH đã nhận*/, UNPAID(1) /*KH đã hủy*/, BOOM(2) /*KH không nhận hàng*/, DELIVERY(3) /* đang giao hàng*/, PACKAING(4) /*đang đóng gói*/, ORDER(5) /*xác nhận đơn*/;
        private final Integer value;

        private STATUSPAYMENT(Integer value) {
            this.value = value;
        }

        public Integer value() {
            return this.value;
        }
    }

    public static enum MAILSTATUS {
        PAID(0), UNPAID(1);

        private final Integer value;

        private MAILSTATUS(Integer value) {
            this.value = value;
        }

        public Integer value() {
            return this.value;
        }
    }

}
