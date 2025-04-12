package DAO;

import Models.Doctor;
import Models.Specialist;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DoctorDAO class provides methods to interact with the Doctor table in the database.
 * It includes methods to add, retrieve, update, and delete doctors.
 */
public class DoctorDAO {

    /**
     * Adds a new doctor to the database.
     *
     * @param doctor the doctor to be added
     * @throws SQLException if a database access error occurs
     */
    public void addDoctor(Doctor doctor) throws SQLException {
        String sql;
        // Check if the doctor is a Specialist
        // If yes, include specialization and experience in the SQL query
        if (doctor instanceof Specialist) {
            sql = "INSERT INTO Doctor (doctorid, firstname, surname, address, email, hospital, specialization, experience) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            // If not a Specialist, exclude specialization and experience
            sql = "INSERT INTO Doctor (doctorid, firstname, surname, address, email, hospital) VALUES (?, ?, ?, ?, ?, ?)";
        }

        // Use a try-with-resources block to automatically close the database connection and statement
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values for the doctor fields in the SQL query
            stmt.setInt(1, doctor.getDoctorId());
            stmt.setString(2, doctor.getFirstName());
            stmt.setString(3, doctor.getSurname());
            stmt.setString(4, doctor.getAddress());
            stmt.setString(5, doctor.getEmail());
            stmt.setString(6, doctor.getHospital());

            // If the doctor is a Specialist, set the specialization and experience fields
            if (doctor instanceof Specialist) {
                Specialist specialist = (Specialist) doctor;
                stmt.setString(7, specialist.getSpecialization());
                stmt.setString(8, specialist.getExperience());
            }

            // Execute the SQL query to insert the doctor into the database
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all doctors from the database.
     *
     * @return a list of all doctors
     * @throws SQLException if a database access error occurs
     */
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor"; // SQL query to get all doctors

        // Use a try-with-resources block to automatically close the connection, statement, and result set
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Loop through the result set to process each row
            while (rs.next()) {
                // Get the values for each column in the current row
                int doctorid = rs.getInt("doctorid");
                String firstname = rs.getString("firstname");
                String surname = rs.getString("surname");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String hospital = rs.getString("hospital");
                String specialization = rs.getString("specialization");
                String experience = rs.getString("experience");

                // Check if the doctor has a specialization
                // If yes, create a Specialist object
                // If no, create a regular Doctor object
                Doctor doctor;
                if (specialization != null) {
                    doctor = new Specialist(doctorid, firstname, surname, address, email, hospital, specialization, experience);
                } else {
                    doctor = new Doctor(doctorid, firstname, surname, address, email, hospital);
                }

                // Add the doctor to the list
                doctors.add(doctor);
            }
        }
        return doctors; // Return the list of doctors
    }

    /**
     * Retrieves a doctor by its ID.
     *
     * @param doctorId the ID of the doctor to retrieve
     * @return the doctor with the specified ID, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Doctor getDoctorById(int doctorId) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE doctorid = ?"; // SQL query to get a doctor by ID
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId); // Set the doctor ID in the query

            try (ResultSet rs = stmt.executeQuery()) {
                // Check if a row is returned
                if (rs.next()) {
                    // Get the specialization and experience fields
                    String specialization = rs.getString("specialization");
                    String experience = rs.getString("experience");

                    // If the doctor has a specialization, return a Specialist object
                    // Otherwise, return a regular Doctor object
                    if (specialization != null) {
                        return new Specialist(
                                rs.getInt("doctorid"),
                                rs.getString("firstname"),
                                rs.getString("surname"),
                                rs.getString("address"),
                                rs.getString("email"),
                                rs.getString("hospital"),
                                specialization, experience);
                    } else {
                        return new Doctor(
                                rs.getInt("doctorid"),
                                rs.getString("firstname"),
                                rs.getString("surname"),
                                rs.getString("address"),
                                rs.getString("email"),
                                rs.getString("hospital"));
                    }
                }
            }
        }
        return null; // Return null if no doctor is found
    }

    /**
     * Updates the details of an existing doctor in the database.
     *
     * @param doctor the doctor with updated details
     * @throws SQLException if a database access error occurs
     */
    public void updateDoctor(Doctor doctor) throws SQLException {
        String sql;
        // Check if the doctor is a Specialist
        // If yes, include specialization and experience in the SQL query
        if (doctor instanceof Specialist) {
            sql = "UPDATE Doctor SET firstname = ?, surname = ?, address = ?, email = ?, hospital = ?, specialization = ?, experience = ? WHERE doctorid = ?";
        } else {
            // If not a Specialist, exclude specialization and experience
            sql = "UPDATE Doctor SET firstname = ?, surname = ?, address = ?, email = ?, hospital = ? WHERE doctorid = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values for the doctor fields in the SQL query
            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getSurname());
            stmt.setString(3, doctor.getAddress());
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getHospital());

            // If the doctor is a Specialist, set the specialization and experience fields
            if (doctor instanceof Specialist) {
                Specialist specialist = (Specialist) doctor;
                stmt.setString(6, specialist.getSpecialization());
                stmt.setString(7, specialist.getExperience());
                stmt.setInt(8, doctor.getDoctorId());
            } else {
                stmt.setInt(6, doctor.getDoctorId());
            }

            // Execute the SQL query to update the doctor in the database
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a doctor from the database by its ID.
     *
     * @param doctorid the ID of the doctor to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteDoctor(int doctorid) throws SQLException {
        String sql = "DELETE FROM Doctor WHERE doctorid = ?"; // SQL query to delete a doctor by ID
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorid); // Set the doctor ID in the query
            stmt.executeUpdate(); // Execute the SQL query to delete the doctor
        }
    }

    /**
     * Searches for doctors in the database based on a search text.
     *
     * @param searchText the text to search for
     * @return a list of doctors that match the search text
     */
    public List<Doctor> searchDoctors(String searchText) {
        List<Doctor> doctors = new ArrayList<>();
        // SQL query to search for doctors by matching the search text in various fields
        String sql = "SELECT * FROM Doctor WHERE firstname LIKE ? OR surname LIKE ? OR address LIKE ? OR email LIKE ? OR hospital LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Add wildcards to the search text for partial matching
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            stmt.setString(5, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set to process each row
                while (rs.next()) {
                    // Get the values for each column in the current row
                    int doctorid = rs.getInt("doctorid");
                    String firstname = rs.getString("firstname");
                    String surname = rs.getString("surname");
                    String address = rs.getString("address");
                    String email = rs.getString("email");
                    String hospital = rs.getString("hospital");
                    String specialization = rs.getString("specialization");
                    String experience = rs.getString("experience");

                    // Check if the doctor has a specialization
                    // If yes, create a Specialist object
                    // If no, create a regular Doctor object
                    Doctor doctor;
                    if (specialization != null) {
                        doctor = new Specialist(doctorid, firstname, surname, address, email, hospital, specialization, experience);
                    } else {
                        doctor = new Doctor(doctorid, firstname, surname, address, email, hospital);
                    }

                    // Add the doctor to the list
                    doctors.add(doctor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the error if something goes wrong
        }
        return doctors; // Return the list of doctors
    }
}