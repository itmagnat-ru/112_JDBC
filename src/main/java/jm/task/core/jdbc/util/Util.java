package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // todo реализуйте настройку соеденения с БД
    private static final String HOST = "localhost";
    private static final String DBNAME = "kata_users";
    private static final String LOGIN = "root";
    private static final String PASS = "root";

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
