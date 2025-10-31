package com.akmal.observer;

import com.akmal.model.InventoryItem;

public class AlertService implements StockObserver{
    public void onLowStock(InventoryItem item){
        if(item.getReorder() > item.getQuantity()){
            System.out.println("Low Stock Of The Product : "+item.getProduct().getName());
        }
    }
}
