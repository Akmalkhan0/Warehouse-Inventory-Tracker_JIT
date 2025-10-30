package com.akmal.observer;

import com.akmal.model.Product;

public interface StockObserver {
    void onLowStock(Product product);
}
