package com.example.android.customerapp.models;

import java.io.Serializable;

public class Ingredient implements Serializable {

    String name,category,unit,country,city;
    int id,price,safetyStock,stock;
    double kcal;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(int safetyStock) {
        this.safetyStock = safetyStock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Ingredient(int id, double kcal, int price, int safetyStock, int stock, String name, String category, String unit, String country, String city) {
        this.id = id;
        this.kcal = kcal;
        this.price = price;
        this.safetyStock = safetyStock;
        this.stock = stock;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.country = country;
        this.city = city;
    }
}
