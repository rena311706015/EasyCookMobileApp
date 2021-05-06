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
    public Member(String account, String password){
        this.account = account;
        this.password = password;
    }

    public String id, account, password, email, username, phone, role;
}
