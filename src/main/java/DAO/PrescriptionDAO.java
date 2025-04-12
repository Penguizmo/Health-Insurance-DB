package DAO;

import Models.Prescription;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The PrescriptionDAO class provides methods to interact with the Prescription table in the database.
 * It includes methods to add, retrieve, update, and delete prescription information.
 */
public class PrescriptionDAO {

    /**
     * Adds a new prescription to the database.
     *
     * @param prescription the prescription to be added
     * @throws SQLException if a database access error occurs
     */
    public void addPrescription(Prescription prescription) throws SQLException {
        // SQL query to insert a new prescription into the Prescription table
        String sql = "INSERT INTO Prescription (prescriptionid, dateprescribed, dosage, duration, comment, drugid, doctorid, patientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values for the prescription fields in the SQL query
            stmt.setInt(1, prescription.getPrescriptionId());
            stmt.setDate(2, new java.sql.Date(prescription.getDatePrescribed().getTime()));
            stmt.setString(3, prescription.getDosage());
            stmt.setString(4, prescription.getDuration());
            stmt.setString(5, prescription.getComment());
            stmt.setInt(6, prescription.getDrugId());
            stmt.setInt(7, prescription.getDoctorId());
            stmt.setString(8, prescription.getPatientID());
            // Execute the query to add the prescription to the database
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all prescriptions from the database, ordered by prescription date (most recent first).
     *
     * @return a list of all prescriptions
     * @throws SQLException if a database access error occurs
     */
    public List<Prescription> getAllPrescriptions() throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        // SQL query to select all prescriptions, ordered by date
        String sql = "SELECT * FROM Prescription ORDER BY dateprescribed DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Loop through the result set to process each row
            while (rs.next()) {
                // Create a Prescription object using the data from the current row
                prescriptions.add(new Prescription(
                        rs.getInt("prescriptionid"),
                        rs.getDate("dateprescribed"),
                        rs.getString("dosage"),
                        rs.getString("duration"),
                        rs.getString("comment"),
                        rs.getInt("drugid"),
                        rs.getInt("doctorid"),
                        rs.getString("patientID")
                ));
            }
        }
        return prescriptions; // Return the list of prescriptions
    }

    /**
     * Retrieves a specific prescription from the database by its ID.
     *
     * @param prescriptionid the unique identifier of the prescription to retrieve
     * @return the prescription object if found, or null if no prescription exists with the given ID
     * @throws SQLException if a database access error occurs
     */
    public Prescription getPrescriptionById(int prescriptionid) throws SQLException {
        // SQL query to select a prescription by its ID
        String sql = "SELECT * FROM Prescription WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the prescription ID in the query
            stmt.setInt(1, prescriptionid);
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if a row is returned
                if (rs.next()) {
                    // Create and return a Prescription object using the data from the row
                    return new Prescription(
                            rs.getInt("prescriptionid"),
                            rs.getDate("dateprescribed"),
                            rs.getString("dosage"),
                            rs.getString("duration"),
                            rs.getString("comment"),
                            rs.getInt("drugid"),
                            rs.getInt("doctorid"),
                            rs.getString("patientID")
                    );
                }
            }
        }
        return null; // Return null if no prescription is found
    }

    /**
     * Updates an existing prescription record in the database.
     *
     * @param prescription the prescription with updated information
     * @throws SQLException if a database access error occurs
     */
    public void updatePrescription(Prescription prescription) throws SQLException {
        // SQL query to update a prescription's details in the Prescription table
        String sql = "UPDATE Prescription SET dateprescribed = ?, dosage = ?, duration = ?, comment = ?, drugid = ?, doctorid = ?, patientID = ? WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the updated values for the prescription fields in the SQL query
            stmt.setDate(1, new java.sql.Date(prescription.getDatePrescribed().getTime()));
            stmt.setString(2, prescription.getDosage());
            stmt.setString(3, prescription.getDuration());
            stmt.setString(4, prescription.getComment());
            stmt.setInt(5, prescription.getDrugId());
            stmt.setInt(6, prescription.getDoctorId());
            stmt.setString(7, prescription.getPatientID());
            stmt.setInt(8, prescription.getPrescriptionId());
            // Execute the query to update the prescription in the database
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a prescription from the database by its ID.
     *
     * @param prescriptionId the ID of the prescription to delete
     * @throws SQLException if a database access error occurs
     */
    public void deletePrescription(int prescriptionId) throws SQLException {
        // SQL query to delete a prescription by its ID
        String sql = "DELETE FROM Prescription WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the prescription ID in the query
            stmt.setInt(1, prescriptionId);
            // Execute the query to delete the prescription from the database
            stmt.executeUpdate();
        }
    }
}