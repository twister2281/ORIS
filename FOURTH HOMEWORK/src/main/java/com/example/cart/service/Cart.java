package com.example.cart.service;

import com.example.cart.model.CartItem;
import com.example.cart.model.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void add(Product product) {
        CartItem existing = items.get(product.getId());
        if (existing == null) {
            items.put(product.getId(), new CartItem(product));
        } else {
            existing.increment();
        }
    }

    public void remove(String productId) { items.remove(productId); }
    public Collection<CartItem> getItems() { return items.values(); }
    public boolean isEmpty() { return items.isEmpty(); }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

