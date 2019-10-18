package com.vn.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vnpay_trans_info")
@NamedQuery(name = "VnpayTransactionInfo.findAll", query = "SELECT vnp FROM VnpayTransactionInfo vnp")
public class VnpayTransactionInfo implements Serializable {

    private static final Long serizlizable = 1L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name="vnp_locale")
    private String vnpLocale;

    @Column(name="vnp_curr_code")
    private String vnpCurrCode;

    @Column(name="vnp_order_info")
    private String vnpOrderInfo;

    @Column(name="vnp_order_type")
    private String vnpOrderType;

    @Column(name="vnp_amount")
    private Long vnpAmount;

    @Column(name="vnp_ip_addr")
    private String vnpIpAddr;

    @Column(name="vnp_create_date")
    private String vnpCreateDate;

    @Column(name="vnp_bank_code")
    private String vnpBankCode;

    @Column(name="vnp_bank_tran_no")
    private String vnpBankTranNo;

    @Column(name="vnp_pay_date")
    private String vnpPayDate;

    @Column(name="vnp_transaction_no")
    private String vnpTransactionNo;

    @Column(name="vnp_response_code")
    private String vnpResponseCode;

    @Column(name="id_bill")
    private Long idBill;

    @Column(name="code")
    private String code;

    @Column(name = "status")
    private Integer status;

    public VnpayTransactionInfo() {
    }

    public VnpayTransactionInfo(Date createdDate, String vnpLocale, String vnpCurrCode, String vnpOrderInfo, String vnpOrderType, Long vnpAmount, String vnpIpAddr, String vnpCreateDate, String vnpBankCode, String vnpBankTranNo, String vnpPayDate, String vnpTransactionNo, String vnpResponseCode, Long idBill, String code, Integer status) {
        this.createdDate = createdDate;
        this.vnpLocale = vnpLocale;
        this.vnpCurrCode = vnpCurrCode;
        this.vnpOrderInfo = vnpOrderInfo;
        this.vnpOrderType = vnpOrderType;
        this.vnpAmount = vnpAmount;
        this.vnpIpAddr = vnpIpAddr;
        this.vnpCreateDate = vnpCreateDate;
        this.vnpBankCode = vnpBankCode;
        this.vnpBankTranNo = vnpBankTranNo;
        this.vnpPayDate = vnpPayDate;
        this.vnpTransactionNo = vnpTransactionNo;
        this.vnpResponseCode = vnpResponseCode;
        this.idBill = idBill;
        this.code = code;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getVnpLocale() {
        return vnpLocale;
    }

    public void setVnpLocale(String vnpLocale) {
        this.vnpLocale = vnpLocale;
    }

    public String getVnpCurrCode() {
        return vnpCurrCode;
    }

    public void setVnpCurrCode(String vnpCurrCode) {
        this.vnpCurrCode = vnpCurrCode;
    }

    public String getVnpOrderInfo() {
        return vnpOrderInfo;
    }

    public void setVnpOrderInfo(String vnpOrderInfo) {
        this.vnpOrderInfo = vnpOrderInfo;
    }

    public String getVnpOrderType() {
        return vnpOrderType;
    }

    public void setVnpOrderType(String vnpOrderType) {
        this.vnpOrderType = vnpOrderType;
    }

    public Long getVnpAmount() {
        return vnpAmount;
    }

    public void setVnpAmount(Long vnpAmount) {
        this.vnpAmount = vnpAmount;
    }

    public String getVnpIpAddr() {
        return vnpIpAddr;
    }

    public void setVnpIpAddr(String vnpIpAddr) {
        this.vnpIpAddr = vnpIpAddr;
    }

    public String getVnpCreateDate() {
        return vnpCreateDate;
    }

    public void setVnpCreateDate(String vnpCreateDate) {
        this.vnpCreateDate = vnpCreateDate;
    }

    public String getVnpBankCode() {
        return vnpBankCode;
    }

    public void setVnpBankCode(String vnpBankCode) {
        this.vnpBankCode = vnpBankCode;
    }

    public String getVnpBankTranNo() {
        return vnpBankTranNo;
    }

    public void setVnpBankTranNo(String vnpBankTranNo) {
        this.vnpBankTranNo = vnpBankTranNo;
    }

    public String getVnpPayDate() {
        return vnpPayDate;
    }

    public void setVnpPayDate(String vnpPayDate) {
        this.vnpPayDate = vnpPayDate;
    }

    public String getVnpTransactionNo() {
        return vnpTransactionNo;
    }

    public void setVnpTransactionNo(String vnpTransactionNo) {
        this.vnpTransactionNo = vnpTransactionNo;
    }

    public String getVnpResponseCode() {
        return vnpResponseCode;
    }

    public void setVnpResponseCode(String vnpResponseCode) {
        this.vnpResponseCode = vnpResponseCode;
    }

    public Long getIdBill() {
        return idBill;
    }

    public void setIdBill(Long idBill) {
        this.idBill = idBill;
    }

    @PrePersist
    public void prepersist(){
        if(this.createdDate == null){
            this.createdDate = new Date();
        }
    }

    public static enum VnpayTranStatus {

        UNPAID(0), PAID(1), FAILURE(2);

        private final int value;

        private VnpayTranStatus(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
