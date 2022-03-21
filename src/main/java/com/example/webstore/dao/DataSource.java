package com.example.webstore.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import static com.example.webstore.dao.Messages.CANNOT_OBTAIN_CONNECTION;

public class DataSource {

    private static final Logger logger = Logger.getLogger("DataSourceLogger");
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test2db");
        config.setUsername("root");
        config.setPassword("Epamproject2022");
        config.addDataSourceProperty("cachePrepStmts" , "true");
        config.addDataSourceProperty("prepStmtCacheSize" , "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit" , "2048");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(20000);
        config.setIdleTimeout(20000);
        config.setMaxLifetime(20000);

        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException ex) {
            logger.info(CANNOT_OBTAIN_CONNECTION.toString());
            ex.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.info(Messages.CANNOT_CLOSE_CONNECTION.toString());
            }
        }
    }

}
