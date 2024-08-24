package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
    private static final String URL = "jdbc:mysql://localhost:3306/ihl_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Password1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private DatabaseUtility() {
        throw new IllegalStateException("Utility class");
    }
}
