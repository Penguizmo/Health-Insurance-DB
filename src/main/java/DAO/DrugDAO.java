package DAO;

import Models.Drug;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DrugDAO class provides methods to interact with the Drug table in the database.
 * It includes methods to add, retrieve, update, and delete drugs.
 */
public class DrugDAO {

    /**
     * Adds a new drug to the database.
     *
     * @param drug the drug to be added
     * @throws SQLException if a database access error occurs
     */
    public void addDrug(Drug drug) throws SQLException {
        // SQL query to insert a new drug into the Drug table
        String sql = "INSERT INTO Drug (drugid, drugname, sideeffects, benefits) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values for the drug fields in the SQL query
            stmt.setInt(1, drug.getDrugId());
            stmt.setString(2, drug.getDrugName());
            stmt.setString(3, drug.getSideEffects());
            stmt.setString(4, drug.getBenefits());
            // Execute the query to add the drug to the database
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all drugs from the database.
     *
     * @return a list of all drugs
     * @throws SQLException if a database access error occurs
     */
    public List<Drug> getAllDrugs() throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        // SQL query to select all drugs from the Drug table
        String sql = "SELECT * FROM Drug";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Loop through the result set to process each row
            while (rs.next()) {
                // Create a Drug object using the data from the current row
                Drug drug = new Drug(
                        rs.getInt("drugid"),
                        rs.getString("drugname"),
                        rs.getString("sideeffects"),
                        rs.getString("benefits"));
                // Add the drug to the list
                drugs.add(drug);
            }
        }
        return drugs; // Return the list of drugs
    }

    /**
     * Retrieves a drug by its ID.
     *
     * @param drugId the ID of the drug to retrieve
     * @return the drug with the specified ID, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Drug getDrugById(int drugId) throws SQLException {
        // SQL query to select a drug by its ID
        String sql = "SELECT * FROM Drug WHERE drugid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the drug ID in the query
            stmt.setInt(1, drugId);
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if a row is returned
                if (rs.next()) {
                    // Create and return a Drug object using the data from the row
                    return new Drug(
                            rs.getInt("drugid"),
                            rs.getString("drugname"),
                            rs.getString("sideeffects"),
                            rs.getString("benefits"));
                }
            }
        }
        return null; // Return null if no drug is found
    }

    /**
     * Updates the details of an existing drug in the database.
     *
     * @param drug the drug with updated details
     * @throws SQLException if a database access error occurs
     */
    public void updateDrug(Drug drug) throws SQLException {
        // SQL query to update a drug's details in the Drug table
        String sql = "UPDATE Drug SET drugname = ?, sideeffects = ?, benefits = ? WHERE drugid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the updated values for the drug fields in the SQL query
            stmt.setString(1, drug.getDrugName());
            stmt.setString(2, drug.getSideEffects());
            stmt.setString(3, drug.getBenefits());
            stmt.setInt(4, drug.getDrugId());
            // Execute the query to update the drug in the database
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a drug from the database by its ID.
     *
     * @param drugId the ID of the drug to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteDrug(int drugId) throws SQLException {
        // SQL query to delete a drug by its ID
        String sql = "DELETE FROM Drug WHERE drugid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the drug ID in the query
            stmt.setInt(1, drugId);
            // Execute the query to delete the drug from the database
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves drugs by a specified column and value.
     *
     * @param column the column to search by
     * @param value the value to search for
     * @return a list of drugs matching the specified column and value
     * @throws SQLException if a database access error occurs
     */
    private List<Drug> getDrugsByColumn(String column, String value) throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        // SQL query to select drugs by a specific column and value
        String sql = "SELECT * FROM Drug WHERE " + column + " = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the value to search for in the query
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set to process each row
                while (rs.next()) {
                    // Create a Drug object using the data from the current row
                    Drug drug = new Drug(
                            rs.getInt("drugid"),
                            rs.getString("drugname"),
                            rs.getString("sideeffects"),
                            rs.getString("benefits"));
                    // Add the drug to the list
                    drugs.add(drug);
                }
            }
        }
        return drugs; // Return the list of drugs
    }

    /**
     * Retrieves drugs by their name.
     *
     * @param drugName the name of the drug to search for
     * @return a list of drugs with the specified name
     * @throws SQLException if a database access error occurs
     */
    public List<Drug> getDrugsByName(String drugName) throws SQLException {
        // Call the helper method to search for drugs by the "drugname" column
        return getDrugsByColumn("drugname", drugName);
    }

    /**
     * Retrieves drugs by their side effects.
     *
     * @param sideEffects the side effects of the drug to search for
     * @return a list of drugs with the specified side effects
     * @throws SQLException if a database access error occurs
     */
    public List<Drug> getDrugsBySideEffects(String sideEffects) throws SQLException {
        // Call the helper method to search for drugs by the "sideeffects" column
        return getDrugsByColumn("sideeffects", sideEffects);
    }
}