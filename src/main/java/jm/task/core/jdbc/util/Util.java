package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String dbURL = "jdbc:mysql://localhost:3306/my_db";
    private static String dbUSER_NAME = "root";
    private static String dbPASSWORD = "springcourse";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, dbUSER_NAME, dbPASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка соединения");
        }
        return connection;
    }

}
