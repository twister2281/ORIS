package com.example.crud.model;

import java.math.BigDecimal;

public class CartLine {
    private final Product product; private final int qty; private final BigDecimal lineTotal;
    public CartLine(Product product, int qty){ this.product=product; this.qty=qty; this.lineTotal=product.getPrice().multiply(BigDecimal.valueOf(qty)); }
    public Product getProduct(){ return product; }
    public int getQty(){ return qty; }
    public BigDecimal getLineTotal(){ return lineTotal; }
}

