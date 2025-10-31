package com.akmal.model;

public class InventoryItem {
    private Product product;
    private int quantity;
    private int reorder;

    public InventoryItem(Product product, int quantity, int reorder) {
        this.product = product;
        this.quantity = quantity;
        this.reorder = reorder;
    }

    public Product getProduct() {
        return product;
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

    public String getProductId() {
        return product.getProductId();
    }

    public String getProductName() {
        return product.getName();
    }
}