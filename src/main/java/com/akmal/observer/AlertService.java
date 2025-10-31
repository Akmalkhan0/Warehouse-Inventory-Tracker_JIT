package com.akmal.observer;

import com.akmal.model.InventoryItem;

public class AlertService implements StockObserver{
    public void onLowStock(InventoryItem item){
        System.out.println("Low Stock Of The Product : "+item.getProduct().getName());
    }
}
