package com.example.android.customerapp.models;

public class Member {
    public Member(String account, String password, String phone, String email, String username, String role) {
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public Member(String account, String password, String phone, String email, String username) {
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.username = username;
    }

    public Member(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String id, account, password, email, username, phone, role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
