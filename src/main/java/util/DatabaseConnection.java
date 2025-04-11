package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

                String url = "jdbc:mysql://localhost:3306/healthinsurancedb";
                String user = "root";
                String password = "root";

                connection = DriverManager.getConnection(url, user, password);
            } catch (IOException | SQLException e) {
                throw new RuntimeException("Error connecting to database", e);
            }
        }
        return connection;
    }
}