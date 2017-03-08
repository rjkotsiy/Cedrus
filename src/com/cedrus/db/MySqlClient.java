package com.cedrus.db;

import com.cedrus.logger.ApplicationLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlClient {


    private static final String CONNECTION_CLOSED_MSG = "Connection closed";
    private static final String CONNECTION_OPEN_MSG = "Connection open";
    private static final String CHECK_CONNECTION = "Check connection method start";

    private final String databaseUrl;
    private final String username;
    private final String password;

    private ApplicationLogger logger = new ApplicationLogger(this.getClass());


    public MySqlClient(String databaseUrl, String username, String password) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            logger.debug("driver successfully registered");
        } catch (SQLException e) {
            logger.error("error during registering driver" + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, username, password);
    }

    public boolean checkConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn != null) {
                logger.debug(CHECK_CONNECTION);
                logger.debug(CONNECTION_OPEN_MSG);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                logger.debug(CONNECTION_CLOSED_MSG);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }
}