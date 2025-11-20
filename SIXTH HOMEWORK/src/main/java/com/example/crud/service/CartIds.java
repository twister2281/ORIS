package com.example.crud.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class CartIds {
    private final Map<Long,Integer> qty=new LinkedHashMap<>();
    public void add(long productId){ qty.merge(productId,1,Integer::sum); }
    public void remove(long productId){ qty.remove(productId); }
    public Map<Long,Integer> getAll(){ return qty; }
    public boolean isEmpty(){ return qty.isEmpty(); }
    public void clear(){ qty.clear(); }
}

