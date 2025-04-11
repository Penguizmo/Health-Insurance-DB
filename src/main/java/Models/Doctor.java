package Models;

/**
 * The Doctor class represents a medical doctor with basic personal information.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the doctor.
 */
public class Doctor {
    // Fields to store the doctor's information
    private int doctorid; // Unique identifier for the doctor
    private String firstname; // Doctor's first name
    private String surname; // Doctor's surname or last name
    private String address; // Doctor's physical address
    private String email; // Doctor's email contact
    private String hospital; // Name of the hospital where the doctor is affiliated

    /**
     * Default constructor.
     * This method is called when a new Doctor object is created without any details.
     */
    public Doctor() {}

    /**
     * Constructor.
     * This method is called when a new Doctor object is created with specific details.
     * It takes parameters to set the fields of the class.
     *
     * @param doctorid  The unique identifier for the doctor
     * @param firstname The doctor's first name
     * @param surname   The doctor's surname or last name
     * @param address   The doctor's physical address
     * @param email     The doctor's email contact
     * @param hospital  The name of the hospital where the doctor is affiliated
     */
    public Doctor(int doctorid, String firstname, String surname, String address, String email, String hospital) {
        this.doctorid = doctorid;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.hospital = hospital;
    }

    public Doctor(String doctorid, String firstName, String surname, String address, String email, String hospital, String specialization, String experience) {
    }

    /**
     * Getter method for doctorid.
     * This method returns the value of the doctorid field.
     *
     * @return The unique identifier for the doctor
     */
    public int getDoctorId() {
        return doctorid;
    }

    /**
     * Setter method for doctorid.
     * This method sets the value of the doctorid field.
     *
     * @param doctorid The unique identifier for the doctor
     */
    public void setDoctorId(int doctorid) {
        this.doctorid = doctorid;
    }

    /**
     * Getter method for firstname.
     * This method returns the value of the firstname field.
     *
     * @return The doctor's first name
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Setter method for firstname.
     * This method sets the value of the firstname field.
     *
     * @param firstname The doctor's first name
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter method for surname.
     * This method returns the value of the surname field.
     *
     * @return The doctor's surname or last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter method for surname.
     * This method sets the value of the surname field.
     *
     * @param surname The doctor's surname or last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter method for address.
     * This method returns the value of the address field.
     *
     * @return The doctor's physical address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for address.
     * This method sets the value of the address field.
     *
     * @param address The doctor's physical address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for email.
     * This method returns the value of the email field.
     *
     * @return The doctor's email contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email.
     * This method sets the value of the email field.
     *
     * @param email The doctor's email contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for hospital.
     * This method returns the value of the hospital field.
     *
     * @return The name of the hospital where the doctor is affiliated
     */
    public String getHospital() {
        return hospital;
    }

    /**
     * Setter method for hospital.
     * This method sets the value of the hospital field.
     *
     * @param hospital The name of the hospital where the doctor is affiliated
     */
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    /**
     * Override the toString method to provide a string representation of the doctor.
     * This method returns a formatted string with doctor details.
     *
     * @return A string representation of the doctor
     */
    @Override
    public String toString() {
        return "Doctor [ID=" + doctorid + ", FirstName=" + firstname + ", Surname=" + surname +
                ", Address=" + address + ", Email=" + email + ", Hospital=" + hospital + "]";
    }

    /**
     * Placeholder method for specialization.
     * This method can be extended to return the doctor's specialization.
     *
     * @return An empty string (to be implemented later)
     */
    public String getSpecialization() {
        return "";
    }

    /**
     * Placeholder method for experience.
     * This method can be extended to return the doctor's years of experience.
     *
     * @return An empty string (to be implemented later)
     */
    public String getExperience() {
        return "";
    }
}