package com.alevel.java.ubike.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

public class ConnectionProvider implements Supplier<Connection>, AutoCloseable {

    private final Connection connection;

    public ConnectionProvider(Properties properties) throws SQLException {
        this.connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        this.connection.setReadOnly(true);
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    @Override
    public Connection get() {
        return connection;
    }
}
