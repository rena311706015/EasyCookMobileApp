package com.example.android.customerapp.models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private int id, transportFee, discount, sum;
    private OrderItem[] orderItems;
    private Member member;
    private String orderNumber, orderTime, payWay, serviceWay, hopeDeliverTime, realDeliverTime, shippingTime, status, address;

    public Order(int id, int transportFee, int discount, int sum, OrderItem[] orderItems, Member member, String orderNumber, String orderTime, String payWay, String serviceWay, String hopeDeliverTime, String realDeliverTime, String shippingTime, String status, String address) {
        this.id = id;
        this.transportFee = transportFee;
        this.discount = discount;
        this.sum = sum;
        this.orderItems = orderItems;
        this.member = member;
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
        this.payWay = payWay;
        this.serviceWay = serviceWay;
        this.hopeDeliverTime = hopeDeliverTime;
        this.realDeliverTime = realDeliverTime;
        this.shippingTime = shippingTime;
        this.status = status;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(int transportFee) {
        this.transportFee = transportFee;
    }

    public String getDiscount() {
        return "-" + discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTotal() {
        return "NT$ " + (sum + transportFee - discount);
    }

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderTime() {
        orderTime = orderTime.replace("T", " ");
        orderTime = orderTime.replace("Z", "");
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayWay() {
        switch (payWay){
            case "cashOnDelivery":
                return "貨到付款";
            case "transfer":
                return "銀行轉帳";
            case "creditcard":
                return "信用卡";
            case "payOnline":
                return "電子支付";
        }

        return "其他";
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getServiceWay() {
        switch (serviceWay){
            case "homeDelivery":
                return "宅配";
            case "family":
                return "全家";
            case "seven":
                return "7-11";
        }
        return "其他";
    }

    public void setServiceWay(String serviceWay) {
        this.serviceWay = serviceWay;
    }

    public String getHopeDeliverTime() {
        return hopeDeliverTime;
    }

    public void setHopeDeliverTime(String hopeDeliverTime) {
        this.hopeDeliverTime = hopeDeliverTime;
    }

    public String getRealDeliverTime() {
        return realDeliverTime;
    }

    public void setRealDeliverTime(String realDeliverTime) {
        this.realDeliverTime = realDeliverTime;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getStatus() {
        switch(status){
            case "toConfirm":
                return "待確認";
            case "toDeliver":
                return "待配送";
            case "finish":
                return "已完成";
            case "canceled":
                return "已取消";
        }
        return "待確認";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
