package DAO;

import Models.Prescription;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The PrescriptionDAO class provides methods to interact with the Prescription table in the database.
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
            stmt.setLong(1, prescription.getPrescriptionId());
            stmt.setDate(2, new java.sql.Date(prescription.getDatePrescribed().getTime()));
            stmt.setString(3, prescription.getDosage());
            stmt.setString(4, prescription.getDuration());
            stmt.setString(5, prescription.getComment());
            stmt.setLong(6, prescription.getDrugId());
            stmt.setLong(7, prescription.getDoctorId());
            stmt.setString(8, prescription.getPatientID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding prescription: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all prescriptions from the database.
     *
     * @return a list of all prescriptions
     * @throws SQLException if a database access error occurs
     */
    public List<Prescription> getAllPrescriptions() throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM Prescription ORDER BY dateprescribed DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                prescriptions.add(mapResultSetToPrescription(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving prescriptions: " + e.getMessage(), e);
        }
        return prescriptions;
    }

    /**
     * Retrieves a specific prescription by its ID.
     *
     * @param prescriptionId the unique identifier of the prescription
     * @return the prescription object if found, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Prescription getPrescriptionById(long prescriptionId) throws SQLException {
        String sql = "SELECT * FROM Prescription WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, prescriptionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrescription(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving prescription by ID: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Updates an existing prescription in the database.
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
            stmt.setLong(5, prescription.getDrugId());
            stmt.setLong(6, prescription.getDoctorId());
            stmt.setString(7, prescription.getPatientID());
            stmt.setLong(8, prescription.getPrescriptionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating prescription: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a prescription from the database by its ID.
     *
     * @param prescriptionId the ID of the prescription to delete
     * @throws SQLException if a database access error occurs
     */
    public void deletePrescription(long prescriptionId) throws SQLException {
        String sql = "DELETE FROM Prescription WHERE prescriptionid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, prescriptionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting prescription: " + e.getMessage(), e);
        }
    }

    /**
     * Searches for prescriptions in the database based on the search text.
     *
     * @param searchText the text to search for
     * @return a list of prescriptions matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    public List<Prescription> searchPrescriptions(String searchText) throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM Prescription WHERE CAST(prescriptionid AS CHAR) LIKE ? OR patientID LIKE ? OR comment LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    prescriptions.add(mapResultSetToPrescription(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error searching prescriptions: " + e.getMessage(), e);
        }
        return prescriptions;
    }

    /**
     * Validates if a Drug ID exists in the database.
     *
     * @param drugID the Drug ID to validate
     * @return true if the Drug ID exists, false otherwise
     */
    public boolean isDrugIdValid(long drugID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Drug WHERE DrugID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, drugID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error validating Drug ID: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Validates if a Doctor ID exists in the database.
     *
     * @param doctorID the Doctor ID to validate
     * @return true if the Doctor ID exists, false otherwise
     */
    public boolean isDoctorIdValid(long doctorID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Doctor WHERE DoctorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, doctorID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error validating Doctor ID: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Validates if a Patient ID exists in the database.
     *
     * @param patientID the Patient ID to validate
     * @return true if the Patient ID exists, false otherwise
     */
    public boolean isPatientIdValid(String patientID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Patient WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error validating Patient ID: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Maps a ResultSet row to a Prescription object.
     *
     * @param rs the ResultSet containing prescription data
     * @return a Prescription object
     * @throws SQLException if a database access error occurs
     */
    private Prescription mapResultSetToPrescription(ResultSet rs) throws SQLException {
        return new Prescription(
                rs.getLong("prescriptionid"),
                rs.getDate("dateprescribed"),
                rs.getString("dosage"),
                rs.getString("duration"),
                rs.getString("comment"),
                rs.getLong("drugid"),
                rs.getLong("doctorid"),
                rs.getString("patientID")
        );
    }
}