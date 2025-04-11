package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.*;

/**
 * The DatabaseConnection class provides a method to establish a connection to the database.
 * It uses a singleton pattern to ensure only one connection is created.
 */
public class DatabaseConnection {
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    /**
     * Returns the database connection. If the connection does not exist, it creates a new one.
     *
     * @return the database connection
     * @throws RuntimeException if there is an error connecting to the database
     */
    public static Connection getConnection() {
        if (connection == null) {
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
                System.out.println("Database connection established successfully."); // Test connection
            } catch (IOException | SQLException e) {
                throw new RuntimeException("Error connecting to database", e);
            }
        }
        return connection;
    }
}