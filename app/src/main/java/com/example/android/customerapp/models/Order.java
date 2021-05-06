package com.example.android.customerapp.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Order implements Serializable {
    private int id, transportFee,discount,sum;
    private List<OrderItem> orderItems;
    private Member member;
    private String orderNumber,orderTime,payWay,serviceWay,hopeDeliverTime,realDeliverTime,shippingTime,status,address;

    public Order(int id, int transportFee, int discount, int sum, List<OrderItem> orderItems, Member member, String orderNumber, String orderTime, String payWay, String serviceWay, String hopeDeliverTime, String realDeliverTime, String shippingTime, String status, String address) {
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
        return "-"+discount;
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

    public int getSubTotal() { return sum+discount-transportFee; }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
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
        orderTime = orderTime.replace("T"," ");
        orderTime = orderTime.replace("Z","");
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getServiceWay() {
        return serviceWay;
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
        return status;
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
