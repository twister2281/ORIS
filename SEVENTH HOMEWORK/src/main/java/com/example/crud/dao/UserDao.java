package com.example.crud.dao;

import com.example.crud.db.DataSourceProvider;
import com.example.crud.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserDao {
    public UserDao(){
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); Statement st=c.createStatement()){
            st.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY PRIMARY KEY, username VARCHAR(100) UNIQUE NOT NULL, password_hash VARCHAR(200) NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        } catch (SQLException e){ throw new RuntimeException(e); }
    }
    public Optional<User> findByUsername(String username){
        String sql="SELECT id,username,password_hash,created_at FROM users WHERE username=?";
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,username); try(ResultSet rs=ps.executeQuery()){ if(rs.next()) return Optional.of(map(rs)); return Optional.empty(); }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }
    public User create(String username, String passwordHash){
        String sql="INSERT INTO users(username,password_hash) VALUES(?,?)";
        try(Connection c=DataSourceProvider.getDataSource().getConnection(); PreparedStatement ps=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,username); ps.setString(2,passwordHash); ps.executeUpdate();
            try(ResultSet rs=ps.getGeneratedKeys()){ if(rs.next()) return new User(rs.getLong(1), username, passwordHash, LocalDateTime.now()); }
            return null;
        } catch (SQLException e){ throw new RuntimeException(e); }
    }
    private User map(ResultSet rs) throws SQLException { return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password_hash"), rs.getTimestamp("created_at").toLocalDateTime()); }
}

