package DAO;

import Models.InsuredPatient;
import Models.Patient;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The PatientDAO class provides methods to interact with the Patient table in the database.
 * It includes methods to add, retrieve, update, and delete patient information.
 */
public class PatientDAO {

    /**
     * Inserts a new patient record into the database.
     *
     * @param patient the patient object to be added to the database
     * @throws SQLException if a database access error occurs
     */
    public void addPatient(Patient patient) throws SQLException {
        String sql;
        if (patient instanceof InsuredPatient) {
            sql = "INSERT INTO Patient (patientID, firstname, surname, postcode, address, phone, email, insuranceID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO Patient (patientID, firstname, surname, postcode, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getPatientId());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getSurname());
            stmt.setString(4, patient.getPostcode());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getPhone());
            stmt.setString(7, patient.getEmail());

            if (patient instanceof InsuredPatient) {
                InsuredPatient insuredPatient = (InsuredPatient) patient;
                stmt.setString(8, insuredPatient.getInsuranceId());
            }

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all patient records from the database.
     *
     * @return a list containing all patients in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String patientID = rs.getString("patientID");
                String firstName = rs.getString("firstname");
                String surname = rs.getString("surname");
                String postcode = rs.getString("postcode");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String insuranceID = rs.getString("insuranceID");

                Patient patient;
                if (insuranceID != null) {
                    patient = new InsuredPatient(patientID, firstName, surname, postcode, address, phone, email, insuranceID);
                } else {
                    patient = new Patient(patientID, firstName, surname, postcode, address, phone, email);
                }
                patients.add(patient);
            }
        }
        return patients;
    }

    /**
     * Retrieves a patient record from the database by its ID.
     *
     * @param patientID the unique identifier of the patient to retrieve
     * @return the patient object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public Patient getPatientById(String patientID) throws SQLException {
        String sql = "SELECT * FROM Patient WHERE patientID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String insuranceID = rs.getString("insuranceID");
                    if (insuranceID != null) {
                        return new InsuredPatient(
                                rs.getString("patientID"),
                                rs.getString("firstname"),
                                rs.getString("surname"),
                                rs.getString("postcode"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                insuranceID);
                    } else {
                        return new Patient(
                                rs.getString("patientID"),
                                rs.getString("firstname"),
                                rs.getString("surname"),
                                rs.getString("postcode"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email"));
                    }
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing patient record in the database.
     *
     * @param patient the patient object containing updated information
     * @throws SQLException if a database access error occurs
     */
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE Patient SET firstname = ?, surname = ?, postcode = ?, address = ?, phone = ?, email = ? WHERE patientID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getSurname());
            stmt.setString(3, patient.getPostcode());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getPhone());
            stmt.setString(6, patient.getEmail());
            stmt.setString(7, patient.getPatientId());
            stmt.executeUpdate();
        }

        if (patient instanceof InsuredPatient) {
            String sqlInsurance = "UPDATE Patient SET insuranceID = ? WHERE patientID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sqlInsurance)) {
                stmt.setString(1, ((InsuredPatient) patient).getInsuranceId());
                stmt.setString(2, patient.getPatientId());
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Deletes a patient record from the database by its ID.
     *
     * @param patientID the unique identifier of the patient to delete
     * @throws SQLException if a database access error occurs
     */
    public void deletePatient(String patientID) throws SQLException {
        String sql = "DELETE FROM Patient WHERE patientID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID);
            stmt.executeUpdate();
        }
    }
}