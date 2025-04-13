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
        // Check if the patient is insured
        // If yes, include the insurance ID in the SQL query
        if (patient instanceof InsuredPatient) {
            sql = "INSERT INTO Patient (patientID, firstname, surname, postcode, address, phone, email, insuranceID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            // If not insured, exclude the insurance ID
            sql = "INSERT INTO Patient (patientID, firstname, surname, postcode, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values for the patient fields in the SQL query
            stmt.setString(1, patient.getPatientId());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getSurname());
            stmt.setString(4, patient.getPostcode());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getPhone());
            stmt.setString(7, patient.getEmail());

            // If the patient is insured, set the insurance ID
            if (patient instanceof InsuredPatient) {
                InsuredPatient insuredPatient = (InsuredPatient) patient;
                stmt.setString(8, insuredPatient.getInsuranceId());
            }

            // Execute the query to add the patient to the database
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
        String sql = "SELECT * FROM Patient"; // SQL query to get all patients

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through the result set to process each row
            while (rs.next()) {
                // Get the values for each column in the current row
                String patientID = rs.getString("patientID");
                String firstName = rs.getString("firstname");
                String surname = rs.getString("surname");
                String postcode = rs.getString("postcode");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String insuranceID = rs.getString("insuranceID");

                // Check if the patient has insurance
                // If yes, create an InsuredPatient object
                // If no, create a regular Patient object
                Patient patient;
                if (insuranceID != null) {
                    patient = new InsuredPatient(patientID, firstName, surname, postcode, address, phone, email, insuranceID);
                } else {
                    patient = new Patient(patientID, firstName, surname, postcode, address, phone, email);
                }

                // Add the patient to the list
                patients.add(patient);
            }
        }
        return patients; // Return the list of patients
    }

    /**
     * Retrieves a patient record from the database by its ID.
     *
     * @param patientID the unique identifier of the patient to retrieve
     * @return the patient object if found, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public Patient getPatientById(String patientID) throws SQLException {
        String sql = "SELECT * FROM Patient WHERE patientID = ?"; // SQL query to get a patient by ID

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID); // Set the patient ID in the query
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if a row is returned
                if (rs.next()) {
                    // Get the insurance ID field
                    String insuranceID = rs.getString("insuranceID");

                    // If the patient has insurance, return an InsuredPatient object
                    // Otherwise, return a regular Patient object
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
        return null; // Return null if no patient is found
    }

    /**
     * Updates an existing patient record in the database.
     *
     * @param patient the patient object containing updated information
     * @throws SQLException if a database access error occurs
     */
    public void updatePatient(Patient patient) throws SQLException {
        // SQL query to update the basic patient details
        String sql = "UPDATE Patient SET firstname = ?, surname = ?, postcode = ?, address = ?, phone = ?, email = ? WHERE patientID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the updated values for the patient fields in the SQL query
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getSurname());
            stmt.setString(3, patient.getPostcode());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getPhone());
            stmt.setString(6, patient.getEmail());
            stmt.setString(7, patient.getPatientId());
            stmt.executeUpdate();
        }

        // If the patient is insured, update the insurance ID
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
        // SQL query to delete a patient by its ID
        String sql = "DELETE FROM Patient WHERE patientID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientID); // Set the patient ID in the query
            stmt.executeUpdate(); // Execute the query to delete the patient
        }
    }

    /**
     * Searches for patients in the database based on a search text
     *
     * @param searchText the text to search for in patient records
     * @return a list of patients matching the search criteria
     */

    public List<Patient> searchPatients(String searchText) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient WHERE firstname LIKE ? OR surname LIKE ? OR postcode LIKE ? OR address LIKE ? OR phone LIKE ? OR email LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the search text for each field in the SQL query
            String searchPattern = "%" + searchText + "%";
            for (int i = 1; i <= 6; i++) {
                stmt.setString(i, searchPattern);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set to process each row
                while (rs.next()) {
                    // Get the values for each column in the current row
                    String patientID = rs.getString("patientID");
                    String firstName = rs.getString("firstname");
                    String surname = rs.getString("surname");
                    String postcode = rs.getString("postcode");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String insuranceID = rs.getString("insuranceID");

                    // Check if the patient has insurance
                    // If yes, create an InsuredPatient object
                    // If no, create a regular Patient object
                    Patient patient;
                    if (insuranceID != null) {
                        patient = new InsuredPatient(patientID, firstName, surname, postcode, address, phone, email, insuranceID);
                    } else {
                        patient = new Patient(patientID, firstName, surname, postcode, address, phone, email);
                    }

                    // Add the patient to the list
                    patients.add(patient);
                }
            }
        }catch (SQLException e) {
        e.printStackTrace(); // Print the stack trace for debugging}
            throw e; // Rethrow the exception to be handled by the caller
    }
        return patients; // Return the list of matching patients
    }
}