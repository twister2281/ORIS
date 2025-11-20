
import java.util.LinkedHashMap;
import java.util.Map;

public class CartIds {
    private final Map<Long,Integer> qty=new LinkedHashMap<>();
    public void add(long id){ qty.merge(id,1,Integer::sum); }
    public void remove(long id){ qty.remove(id); }
    public Map<Long,Integer> getAll(){ return qty; }
    public boolean isEmpty(){ return qty.isEmpty(); }
    public void clear(){ qty.clear(); }
}
package com.example.crud.service;

