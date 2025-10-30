package com.akmal.observer;

import com.akmal.model.Product;

public class AlertService implements StockObserver{
    public void onLowStock(Product product){
        System.out.println("Low Stock Of The Product : "+product.getName());
    }
}
