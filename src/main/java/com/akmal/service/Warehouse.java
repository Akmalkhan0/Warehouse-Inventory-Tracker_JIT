package com.akmal.service;

import com.akmal.model.InventoryItem;
import com.akmal.observer.StockObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    private Map<String, InventoryItem> inventory;

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
    private void notifyObservers(InventoryItem item) {
        for (StockObserver observer : observers) {
            observer.onLowStock(item);
        }
    }

    public void addInventoryItem(InventoryItem item) {
        if (item != null && item.getProductId() != null) {
            inventory.put(item.getProductId(), item);
            System.out.println("INFO: Added product to inventory - " + item.getProductName());
        } else {
            System.err.println("ERROR: Cannot add null item.");
        }
    }

    public void receiveShipment(String productId, int amount) {
        InventoryItem item = inventory.get(productId);
        if (item == null) {
            System.err.println("ERROR: Failed to receive shipment. Product ID " + productId + " not found.");
            return;
        }

        item.setQuantity(item.getQuantity() + amount);
        System.out.println("INFO: Received shipment for " + item.getProductName() + ". New quantity: " + item.getQuantity());
    }

    public void fulfillOrder(String productId, int amount) {
        InventoryItem item = inventory.get(productId);

        if (item == null) {
            System.err.println("ERROR: Failed to fulfill order. Product ID " + productId + " not found.");
            return;
        }


        if (item.getQuantity() < amount) {
            System.err.println("ERROR: Insufficient stock for " + item.getProductName() + ". Required: " + amount + ", Available: " + item.getQuantity());
            return;
        }

        int oldQuantity = item.getQuantity();
        int newQuantity = oldQuantity - amount;
        item.setQuantity(newQuantity);

        System.out.println("INFO: Fulfilled order for " + item.getProductName() + ". Remaining quantity: " + newQuantity);

        if (newQuantity < item.getReorder() && oldQuantity >= item.getReorder()) {
            System.out.println("TRIGGER: Stock for " + item.getProductName() + " fell below threshold. Notifying observers...");
            notifyObservers(item); // Pass the item
        }
    }

    public void printStockStatus(String productId) {
        InventoryItem item = inventory.get(productId);
        if (item != null) {
            System.out.println("STATUS [" + item.getProductName() + "]: Quantity: " + item.getQuantity() + ", Threshold: " + item.getReorder());
        } else {
            System.err.println("STATUS: Product ID " + productId + " not found.");
        }
    }

    public void printAllStock() {
        System.out.println("\n--- Full Inventory Status ---");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (InventoryItem item : inventory.values()) {
            System.out.println("STATUS [" + item.getProductName() + " (ID: " + item.getProductId() + ")]: Quantity: " + item.getQuantity() + ", Threshold: " + item.getReorder());
        }
    }
}