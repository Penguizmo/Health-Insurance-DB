package DAO;

import Models.Visit;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The VisitDAO class provides methods to interact with the Visit table in the database.
 * It includes methods to add, retrieve, update, and delete visit information.
 */
public class VisitDAO {

    /**
     * Inserts a new visit record into the database.
     *
     * @param visit The Visit object containing the data to be inserted
     * @throws SQLException If a database access error occurs
     */
    public void addVisit(Visit visit) throws SQLException {
        String sql = "INSERT INTO Visit (patientID, doctorid, dateofvisit, symptoms, diagnosisid) VALUES (?, ?, ?, ?, ?)";
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

    /**
     * Retrieves all visits from the database.
     *
     * @return A List containing all visit records in the database
     * @throws SQLException If a database access error occurs
     */
    public List<Visit> getAllVisits() throws SQLException {
        List<Visit> visits = new ArrayList<>();
        String sql = "SELECT * FROM Visit";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Visit visit = new Visit(
                        rs.getString("patientID"),
                        rs.getInt("doctorID"),
                        rs.getDate("dateOfVisit").toLocalDate(),
                        rs.getString("symptoms"),
                        rs.getString("diagnosisid")
                );
                visits.add(visit);
            }
        }
        return visits;
    }

    /**
     * Retrieves a specific visit from the database using the composite primary key.
     * The visit is uniquely identified by a combination of patientID, doctorID, and dateOfVisit.
     *
     * @param patientID The ID of the patient involved in the visit
     * @param doctorid The ID of the doctor conducting the visit
     * @param dateofvisit The date when the visit occurred
     * @return The Visit object if found, or null if no matching visit exists
     * @throws SQLException If a database access error occurs
     */
    public Visit getVisit(String patientID, int doctorid, LocalDate dateofvisit) throws SQLException {
        String sql = "SELECT * FROM Visit WHERE patientID = ? AND doctorid = ? AND dateofvisit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            stmt.setInt(2, doctorid);
            stmt.setDate(3, Date.valueOf(dateofvisit));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Visit(
                            rs.getString("patientID"),
                            rs.getInt("doctorID"),
                            rs.getDate("dateOfVisit").toLocalDate(),
                            rs.getString("symptoms"),
                            rs.getString("diagnosisid")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing visit record in the database.
     * Only the symptoms and diagnosis fields can be updated; the composite key
     * (patientID, doctorID, dateOfVisit) is used to identify which record to update.
     *
     * @param visit The Visit object containing the updated information
     * @throws SQLException If a database access error occurs
     */
    public void updateVisit(Visit visit) throws SQLException {
        String sql = "UPDATE Visit SET symptoms = ?, diagnosisid = ? WHERE patientID = ? AND doctorid = ? AND dateofvisit = ?";
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
     * Deletes a visit record from the database using the composite primary key.
     *
     * @param patientID The ID of the patient involved in the visit
     * @param doctorid The ID of the doctor conducting the visit
     * @param dateofvisit The date when the visit occurred
     * @throws SQLException If a database access error occurs
     */
    public void deleteVisit(String patientID, int doctorid, LocalDate dateofvisit) throws SQLException {
        String sql = "DELETE FROM Visit WHERE patientID = ? AND doctorid = ? AND dateofvisit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            stmt.setInt(2, doctorid);
            stmt.setDate(3, Date.valueOf(dateofvisit));
            stmt.executeUpdate();
        }
    }

    /**
     * Determines which doctor has seen a particular patient most frequently,
     * making them the patient's "primary doctor".
     *
     * @param patientID The ID of the patient to find the primary doctor for
     * @return The ID of the doctor who has conducted the most visits with this patient,
     *         or 0 if the patient has no recorded visits
     * @throws SQLException If a database access error occurs
     */
    public int getPrimaryDoctorId(String patientID) throws SQLException {
        String sql = """
                SELECT doctorid, COUNT(*) as visit_count 
                FROM Visit 
                WHERE patientID = ? 
                GROUP BY doctorid 
                ORDER BY visit_count DESC 
                LIMIT 1
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("doctorid");
                }
            }
        }
        return 0;
    }
}