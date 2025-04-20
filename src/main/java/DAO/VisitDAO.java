package DAO;

import Models.Visit;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The VisitDAO class provides methods to interact with the Visit table in the database.
 * It includes methods to add, retrieve, update, delete, and search visit records.
 */
public class VisitDAO {

    /**
     * Adds a new visit record to the database.
     *
     * @param visit the visit record to be added
     * @throws SQLException if a database access error occurs
     */
    public void addVisit(Visit visit) throws SQLException {
        String sql = "INSERT INTO Visit (patientID, doctorID, dateOfVisit, symptoms, diagnosisID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visit.getPatientID());
            stmt.setInt(2, visit.getDoctorid());
            stmt.setDate(3, Date.valueOf(visit.getDateofvisit()));
            stmt.setString(4, visit.getSymptoms());
            stmt.setString(5, visit.getDiagnosisid());
            stmt.executeUpdate();
        }
    }

    public boolean isPatientIDValid(String patientID) {
        String query = "SELECT COUNT(*) FROM Patient WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patientID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all visit records from the database.
     *
     * @return a list of all visit records
     * @throws SQLException if a database access error occurs
     */
    public List<Visit> getAllVisits() throws SQLException {
        List<Visit> visits = new ArrayList<>();
        String sql = "SELECT * FROM Visit";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                visits.add(mapResultSetToVisit(rs));
            }
        }
        return visits;
    }

    /**
     * Retrieves a specific visit record from the database by patient ID, doctor ID, and date of visit.
     *
     * @param patientID   the patient ID
     * @param doctorID    the doctor ID
     * @param dateOfVisit the date of the visit
     * @return the visit record if found, or null if no record exists
     * @throws SQLException if a database access error occurs
     */
    public Visit getVisit(String patientID, int doctorID, LocalDate dateOfVisit) throws SQLException {
        String sql = "SELECT * FROM Visit WHERE patientID = ? AND doctorID = ? AND dateOfVisit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            stmt.setInt(2, doctorID);
            stmt.setDate(3, Date.valueOf(dateOfVisit));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVisit(rs);
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing visit record in the database.
     *
     * @param visit the visit record with updated information
     * @throws SQLException if a database access error occurs
     */
    public void updateVisit(Visit visit) throws SQLException {
        String sql = "UPDATE Visit SET symptoms = ?, diagnosisID = ? WHERE patientID = ? AND doctorID = ? AND dateOfVisit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, visit.getSymptoms());
            stmt.setString(2, visit.getDiagnosisid());
            stmt.setString(3, visit.getPatientID());
            stmt.setInt(4, visit.getDoctorid());
            stmt.setDate(5, Date.valueOf(visit.getDateofvisit()));
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a visit record from the database by patient ID, doctor ID, and date of visit.
     *
     * @param patientID   the patient ID
     * @param doctorID    the doctor ID
     * @param dateOfVisit the date of the visit
     * @throws SQLException if a database access error occurs
     */
    public void deleteVisit(String patientID, int doctorID, LocalDate dateOfVisit) throws SQLException {
        String sql = "DELETE FROM Visit WHERE patientID = ? AND doctorID = ? AND dateOfVisit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            stmt.setInt(2, doctorID);
            stmt.setDate(3, Date.valueOf(dateOfVisit));
            stmt.executeUpdate();
        }
    }

    /**
     * Searches for visit records in the database based on the search text.
     * The search is performed on patientID, doctorID, and symptoms fields.
     *
     * @param searchText the text to search for
     * @return a list of visit records matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    public List<Visit> searchVisits(String searchText) throws SQLException {
        List<Visit> visits = new ArrayList<>();
        String sql = "SELECT * FROM Visit WHERE patientID LIKE ? OR CAST(doctorID AS CHAR) LIKE ? OR symptoms LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    visits.add(mapResultSetToVisit(rs));
                }
            }
        }
        return visits;
    }

    /**
     * Maps a ResultSet row to a Visit object.
     *
     * @param rs the ResultSet containing visit data
     * @return a Visit object
     * @throws SQLException if a database access error occurs
     */
    private Visit mapResultSetToVisit(ResultSet rs) throws SQLException {
        return new Visit(
                rs.getString("patientID"),
                rs.getInt("doctorID"),
                rs.getDate("dateOfVisit").toLocalDate(),
                rs.getString("symptoms"),
                rs.getString("diagnosisID")
        );
    }
}