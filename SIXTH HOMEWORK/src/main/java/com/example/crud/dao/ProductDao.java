package com.example.crud.dao;

import com.example.crud.db.DataSourceProvider;
import com.example.crud.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {
    public ProductDao(){
        try (Connection c = DataSourceProvider.getDataSource().getConnection(); Statement st = c.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS products (id IDENTITY PRIMARY KEY, name VARCHAR(255) NOT NULL, price DECIMAL(10,2) NOT NULL, description VARCHAR(1000))");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Product create(Product p){
        String sql="INSERT INTO products(name,price,description) VALUES (?,?,?)";
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,p.getName()); ps.setBigDecimal(2,p.getPrice()); ps.setString(3,p.getDescription()); ps.executeUpdate();
            try(ResultSet rs=ps.getGeneratedKeys()){ if(rs.next()) p.setId(rs.getLong(1)); }
            return p;
        } catch(SQLException e){ throw new RuntimeException(e); }
    }

    public Optional<Product> findById(long id){
        String sql="SELECT id,name,price,description FROM products WHERE id=?";
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setLong(1,id); try(ResultSet rs=ps.executeQuery()){ if(rs.next()) return Optional.of(mapRow(rs)); return Optional.empty(); }
        } catch(SQLException e){ throw new RuntimeException(e); }
    }

    public List<Product> findAll(){
        String sql="SELECT id,name,price,description FROM products ORDER BY id"; List<Product> list=new ArrayList<>();
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()){
            while(rs.next()) list.add(mapRow(rs));
        } catch(SQLException e){ throw new RuntimeException(e); }
        return list;
    }

    public boolean update(Product p){ if(p.getId()==null) return false; String sql="UPDATE products SET name=?, price=?, description=? WHERE id=?";
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,p.getName()); ps.setBigDecimal(2,p.getPrice()); ps.setString(3,p.getDescription()); ps.setLong(4,p.getId());
            return ps.executeUpdate()==1;
        } catch(SQLException e){ throw new RuntimeException(e); }
    }

    public boolean delete(long id){ String sql="DELETE FROM products WHERE id=?";
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setLong(1,id); return ps.executeUpdate()==1; }
        catch(SQLException e){ throw new RuntimeException(e); }
    }

    private Product mapRow(ResultSet rs) throws SQLException { return new Product(rs.getLong("id"), rs.getString("name"), rs.getBigDecimal("price"), rs.getString("description")); }
}

