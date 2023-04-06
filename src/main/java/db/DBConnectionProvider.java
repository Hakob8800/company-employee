package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    private final static DBConnectionProvider INSTANCE = new DBConnectionProvider();
    private Connection connection;
    private final static String URL = "jdbc:mysql://localhost:3306/company_employee";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    private DBConnectionProvider() {
    }

    public static DBConnectionProvider getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
