package com.akmal.service;

import com.akmal.model.Product;
import com.akmal.observer.StockObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    private Map<String, Product> inventory;
    private List<StockObserver> observers;

    public Warehouse() {
        this.inventory = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public void registerObserver(StockObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(StockObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Product product) {
        for (StockObserver observer : observers) {
            observer.onLowStock(product);
        }
    }

    public void addProduct(Product product) {
        if (product != null && product.getProductId() != null) {
            inventory.put(product.getProductId(), product);
            System.out.println("INFO: Added product - " + product.getName());
        } else {
            System.err.println("ERROR: Cannot add null product or product with null ID.");
        }
    }

    public void receiveShipment(String productId, int amount) {
        Product product = inventory.get(productId);

        if (product == null) {
            System.err.println("ERROR: Failed to receive shipment. Product ID " + productId + " not found.");
            return;
        }

        if (amount <= 0) {
            System.err.println("ERROR: Shipment amount must be positive.");
            return;
        }

        product.setQuantity(product.getQuantity() + amount);
        System.out.println("INFO: Received shipment for " + product.getName() + ". New quantity: " + product.getQuantity());
    }

    public void fulfillOrder(String productId, int amount) {
        Product product = inventory.get(productId);

        if (product == null) {
            System.err.println("ERROR: Failed to fulfill order. Product ID " + productId + " not found.");
            return;
        }

        if (amount <= 0) {
            System.err.println("ERROR: Order amount must be positive.");
            return;
        }

        if (product.getQuantity() < amount) {
            System.err.println("ERROR: Insufficient stock for " + product.getName() + ". Required: " + amount + ", Available: " + product.getQuantity());
            return;
        }

        int oldQuantity = product.getQuantity();
        int newQuantity = oldQuantity - amount;
        product.setQuantity(newQuantity);

        System.out.println("INFO: Fulfilled order for " + product.getName() + ". Remaining quantity: " + newQuantity);

        if (newQuantity < product.getReorder() && oldQuantity >= product.getReorder()) {
            System.out.println("TRIGGER: Stock for " + product.getName() + " fell below threshold. Notifying observers...");
            notifyObservers(product);
        }
    }

    public void printStockStatus(String productId) {
        Product product = inventory.get(productId);
        if (product != null) {
            System.out.println("STATUS [" + product.getName() + "]: Quantity: " + product.getQuantity() + ", Threshold: " + product.getReorder());
        } else {
            System.err.println("STATUS: Product ID " + productId + " not found.");
        }
    }
}