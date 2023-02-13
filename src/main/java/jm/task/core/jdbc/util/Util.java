package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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


    public static SessionFactory getSessionFactory() {
        String connectionURL = "jdbc:mysql://" + HOST + ":3306/" + DBNAME;

        try {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.URL, connectionURL);
            settings.put(Environment.USER, LOGIN);
            settings.put(Environment.PASS, PASS);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.HBM2DDL_AUTO, "DROP");

            settings.put(Environment.SHOW_SQL, "true");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            SessionFactory sessionFactory = configuration.buildSessionFactory();
            System.out.println("Подключение к БД установлено");
            return sessionFactory;

        } catch (Exception e) {
            System.out.println("Ошибка при подключении к БД");
            e.printStackTrace();
            throw e;
        }
    }

}

