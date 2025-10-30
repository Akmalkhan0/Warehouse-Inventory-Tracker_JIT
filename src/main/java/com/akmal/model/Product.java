package com.akmal.model;

public class Product {
    private String productId;
    private String name;
    private int quantity;
    private int reorder;

    public Product(String productId, String name, int reorder, int quantity) {
        this.productId = productId;
        this.name = name;
        this.reorder = reorder;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorder() {
        return reorder;
    }

    public String getName() {
        return name;
    }
}
