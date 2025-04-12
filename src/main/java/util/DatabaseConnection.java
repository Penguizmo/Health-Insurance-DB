package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
                    Properties prop = new Properties();
                    if (input == null) {
                        throw new RuntimeException("Sorry, unable to find database.properties");
                    }
                    prop.load(input);

                    String url = prop.getProperty("db.url");
                    String user = prop.getProperty("db.user");
                    String password = prop.getProperty("db.password");

                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Database connection established successfully.");
                } catch (IOException | SQLException e) {
                    throw new RuntimeException("Error connecting to database", e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking database connection state", e);
        }
        return connection;
    }
}