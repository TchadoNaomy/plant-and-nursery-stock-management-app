/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp;

/**
 *
 * @author Kapnang
 */
public class Order {
    private int oid;
    private int warehouseManagerId;
    private int supplierId;
    private int productId;
    private int quantityRequested;
    private String orderStatus;
    private String orderDate;

    // Constructor, getters, and setters
    // ...

    public Order() {}

    public Order(int oid, int warehouseManagerId, int supplierId, int productId, int quantityRequested, String orderStatus, String orderDate) {
        this.oid = oid;
        this.warehouseManagerId = warehouseManagerId;
        this.supplierId = supplierId;
        this.productId = productId;
        this.quantityRequested = quantityRequested;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getWarehouseManagerId() {
        return warehouseManagerId;
    }

    public void setWarehouseManagerId(int warehouseManagerId) {
        this.warehouseManagerId = warehouseManagerId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityRequested() {
        return quantityRequested;
    }

    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}

