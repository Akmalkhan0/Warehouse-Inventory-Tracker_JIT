package com.akmal.observer;

import com.akmal.model.Product;

public interface Observe {
    void onLowStock(Product product);
}
