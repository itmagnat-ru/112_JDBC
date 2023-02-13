package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String HOST = "mysql49.hostland.ru";
    private static final String DBNAME = "host1335686_kata";
    private static final String LOGIN = "host1335686_kata";
    private static final String PASS = "kata1377";

    public static Connection getConnection() {
        String connectionURL = "jdbc:mysql://" + HOST + ":3306/" + DBNAME;
        try {
            return DriverManager.getConnection(connectionURL, LOGIN, PASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}

