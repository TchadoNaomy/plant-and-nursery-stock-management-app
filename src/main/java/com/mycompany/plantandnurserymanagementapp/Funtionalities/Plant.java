/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp.Funtionalities;


public class Plant {
    private int pid;
    private String name;
    private String description;
    private String category;
    private int quantityInStock;
    private double price;
    private int supplierId;
    private String dateAdded; // Or LocalDate if you want to use Java 8+ time API

    // Constructor, getters, and setters
    // ...
    public Plant() {}

    public Plant(int pid, String name, String description, String category, int quantityInStock, double price, int supplierId, String dateAdded) {
        this.pid = pid;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.supplierId = supplierId;
        this.dateAdded = dateAdded;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}

