package com.solncev.connection;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Марат on 01.07.2017.
 */
public class ConnectionSingleton {
    private final static String DRIVER = "org.h2.Driver";
    private final static String URL = "jdbc:h2:file:C:\\TwitterSearch\\src\\main\\resources\\test";
    private final static String USER = "test";
    private final static String PASSWORD = "test";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException, IOException {
        if (connection == null) {
            final DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(DRIVER);
            dataSource.setUrl(URL);
            dataSource.setUsername(USER);
            dataSource.setPassword(PASSWORD);
            connection = dataSource.getConnection();
        }
        return connection;
    }
}
