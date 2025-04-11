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
        String sql = "INSERT INTO Prescription (prescriptionid, dateprescribed, dosage, duration, comment, drugid, doctorid, patientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prescription.getPrescriptionId());
            stmt.setDate(2, new java.sql.Date(prescription.getDatePrescribed().getTime()));
            stmt.setString(3, prescription.getDosage());
            stmt.setString(4, prescription.getDuration());
            stmt.setString(5, prescription.getComment());
            stmt.setInt(6, prescription.getDrugId());
            stmt.setInt(7, prescription.getDoctorId());
            stmt.setString(8, prescription.getPatientID());
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
        String sql = "SELECT * FROM Prescription ORDER BY dateprescribed DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
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
        return prescriptions;
    }

    /**
     * Retrieves a specific prescription from the database by its ID.
     *
     * @param prescriptionid the unique identifier of the prescription to retrieve
     * @return the prescription object if found, or null if no prescription exists with the given ID
     * @throws SQLException if a database access error occurs
     */
    public Prescription getPrescriptionById(int prescriptionid) throws SQLException {
        String sql = "SELECT * FROM Prescription WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prescriptionid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
        return null;
    }

    /**
     * Updates an existing prescription record in the database.
     *
     * @param prescription the prescription with updated information
     * @throws SQLException if a database access error occurs
     */
    public void updatePrescription(Prescription prescription) throws SQLException {
        String sql = "UPDATE Prescription SET dateprescribed = ?, dosage = ?, duration = ?, comment = ?, drugid = ?, doctorid = ?, patientID = ? WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(prescription.getDatePrescribed().getTime()));
            stmt.setString(2, prescription.getDosage());
            stmt.setString(3, prescription.getDuration());
            stmt.setString(4, prescription.getComment());
            stmt.setInt(5, prescription.getDrugId());
            stmt.setInt(6, prescription.getDoctorId());
            stmt.setString(7, prescription.getPatientID());
            stmt.setInt(8, prescription.getPrescriptionId());
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
        String sql = "DELETE FROM Prescription WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prescriptionId);
            stmt.executeUpdate();
        }
    }
}