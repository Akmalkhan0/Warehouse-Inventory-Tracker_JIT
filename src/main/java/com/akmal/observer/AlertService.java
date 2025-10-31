package com.akmal.observer;

import com.akmal.model.InventoryItem;

public class AlertService implements StockObserver{
<<<<<<< HEAD
    public void onLowStock(InventoryItem item){
        System.out.println("Low Stock Of The Product : "+item.getProduct().getName());
=======
    public void onLowStock(Product product){
        if(product.getReorder() > product.getQuantity()){
            System.out.println("Low Stock Of The Product : "+product.getName());
        }
>>>>>>> 3eef18ac1ea5c3c2b7562c08eff23c2a148d170e
    }
}
