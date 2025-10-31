package com.akmal.observer;

import com.akmal.model.InventoryItem;

public interface StockObserver {
    void onLowStock(InventoryItem product);
}
