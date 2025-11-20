package com.example.cart.service;

import com.example.cart.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductCatalog {
    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        PRODUCTS.add(new Product("1", "Шаурма Классическая", new BigDecimal("199.00")));
        PRODUCTS.add(new Product("2", "Шаурма Острая", new BigDecimal("219.00")));
        PRODUCTS.add(new Product("3", "Шаурма XXL", new BigDecimal("299.00")));
        PRODUCTS.add(new Product("4", "Шаурма Веган", new BigDecimal("249.00")));
    }

    public List<Product> listAll() { return Collections.unmodifiableList(PRODUCTS); }
    public Optional<Product> findById(String id) { return PRODUCTS.stream().filter(p -> p.getId().equals(id)).findFirst(); }
}

