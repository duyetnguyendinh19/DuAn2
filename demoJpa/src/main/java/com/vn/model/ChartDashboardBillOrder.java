package com.vn.model;

public class ChartDashboardBillOrder {

    private String date;
    private Integer total;

    public ChartDashboardBillOrder() {
    }

    public ChartDashboardBillOrder(String date, Integer total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
