package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // todo реализуйте настройку соеденения с БД

    public static Connection getConnection() throws SQLException {

        String hostName = "localhost";
        String dbName = "kata_users";
        String userName = "root";
        String password = "root";

        return getMySQLConnection(hostName, dbName, userName, password);

    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException {

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        return DriverManager.getConnection(connectionURL, userName,
                password);
    }
}
