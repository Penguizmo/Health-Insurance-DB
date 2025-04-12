package DAO;

import Models.Insurance;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The InsuranceDAO class provides methods to interact with the Insurance table in the database.
 * It includes methods to add, retrieve, update, and delete insurance information.
 */
public class InsuranceDAO {

    /**
     * Inserts a new insurance record into the database.
     *
     * @param insurance the insurance object to be added to the database
     * @throws SQLException if a database access error occurs
     */
    public void addInsurance(Insurance insurance) throws SQLException {
        // SQL query to insert a new insurance record into the Insurance table
        String sql = "INSERT INTO Insurance (insuranceID, company, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values for the insurance fields in the SQL query
            stmt.setString(1, insurance.getInsuranceId());
            stmt.setString(2, insurance.getCompany());
            stmt.setString(3, insurance.getAddress());
            stmt.setString(4, insurance.getPhone());
            // Execute the query to add the insurance record to the database
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all insurance records from the database.
     *
     * @return a list containing all insurance providers in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Insurance> getAllInsurances() throws SQLException {
        List<Insurance> insurances = new ArrayList<>();
        // SQL query to select all insurance records from the Insurance table
        String sql = "SELECT * FROM Insurance";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Loop through the result set to process each row
            while (rs.next()) {
                // Create an Insurance object using the data from the current row
                Insurance insurance = new Insurance(
                        rs.getString("insuranceID"),
                        rs.getString("company"),
                        rs.getString("address"),
                        rs.getString("phone"));
                // Add the insurance object to the list
                insurances.add(insurance);
            }
        }
        return insurances; // Return the list of insurance records
    }

    /**
     * Retrieves an insurance record from the database by its ID.
     *
     * @param insuranceId the unique identifier of the insurance provider to retrieve
     * @return the insurance object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public Insurance getInsuranceById(String insuranceId) throws SQLException {
        // SQL query to select an insurance record by its ID
        String sql = "SELECT * FROM Insurance WHERE insuranceID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the insurance ID in the query
            stmt.setString(1, insuranceId);
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if a row is returned
                if (rs.next()) {
                    // Create and return an Insurance object using the data from the row
                    return new Insurance(
                            rs.getString("insuranceID"),
                            rs.getString("company"),
                            rs.getString("address"),
                            rs.getString("phone"));
                }
            }
        }
        return null; // Return null if no insurance record is found
    }

    /**
     * Updates an existing insurance record in the database.
     *
     * @param insurance the insurance object with updated information
     * @throws SQLException if a database access error occurs
     */
    public void updateInsurance(Insurance insurance) throws SQLException {
        // SQL query to update an insurance record in the Insurance table
        String sql = "UPDATE Insurance SET company = ?, address = ?, phone = ? WHERE insuranceID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the updated values for the insurance fields in the SQL query
            stmt.setString(1, insurance.getCompany());
            stmt.setString(2, insurance.getAddress());
            stmt.setString(3, insurance.getPhone());
            stmt.setString(4, insurance.getInsuranceId());
            // Execute the query to update the insurance record in the database
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes an insurance record from the database based on its ID.
     *
     * @param insuranceId the unique identifier of the insurance provider to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteInsurance(String insuranceId) throws SQLException {
        // SQL query to delete an insurance record by its ID
        String sql = "DELETE FROM Insurance WHERE insuranceID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the insurance ID in the query
            stmt.setString(1, insuranceId);
            // Execute the query to delete the insurance record from the database
            stmt.executeUpdate();
        }
    }
}