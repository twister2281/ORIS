package com.example.crud.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {
    private static final HikariDataSource dataSource;
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/seventh_homework_db;AUTO_SERVER=TRUE");
        config.setUsername("sa");
        config.setPassword("");
        config.setMaximumPoolSize(5);
        config.setDriverClassName("org.h2.Driver");
        dataSource = new HikariDataSource(config);
    }
    public static DataSource getDataSource(){ return dataSource; }
}
target/
*.iml
.idea/
.DS_Store
h2*.db

