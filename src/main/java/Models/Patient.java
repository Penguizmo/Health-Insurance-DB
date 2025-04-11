package Models;

/**
 * The Patient class represents a patient with basic personal information.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the patient.
 */
public class Patient {
    // Fields to store the patient's information
    private String patientID; // Unique identifier for the patient
    private String firstname; // Patient's first name
    private String surname; // Patient's surname or last name
    private String postcode; // Patient's postal code
    private String address; // Patient's physical address
    private String phone; // Patient's phone number
    private String email; // Patient's email contact

    /**
     * Default constructor.
     * This method is called when a new Patient object is created without any details.
     */
    public Patient() {}

    /**
     * Constructor.
     * This method is called when a new Patient object is created with specific details.
     * It takes parameters to set the fields of the class.
     *
     * @param patientID The unique identifier for the patient
     * @param firstname The patient's first name
     * @param surname   The patient's surname or last name
     * @param postcode  The patient's postal code
     * @param address   The patient's physical address
     * @param phone     The patient's phone number
     * @param email     The patient's email contact
     */
    public Patient(String patientID, String firstname, String surname, String postcode, String address, String phone, String email) {
        this.patientID = patientID;
        this.firstname = firstname;
        this.surname = surname;
        this.postcode = postcode;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Getter method for patientID.
     * This method returns the value of the patientID field.
     *
     * @return The unique identifier for the patient
     */
    public String getPatientId() {
        return patientID;
    }

    /**
     * Setter method for patientID.
     * This method sets the value of the patientID field.
     *
     * @param patientId The unique identifier for the patient
     */
    public void setPatientId(String patientId) {
        this.patientID = patientId;
    }

    /**
     * Getter method for firstname.
     * This method returns the value of the firstname field.
     *
     * @return The patient's first name
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Setter method for firstname.
     * This method sets the value of the firstname field.
     *
     * @param firstName The patient's first name
     */
    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    /**
     * Getter method for surname.
     * This method returns the value of the surname field.
     *
     * @return The patient's surname or last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter method for surname.
     * This method sets the value of the surname field.
     *
     * @param surname The patient's surname or last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter method for postcode.
     * This method returns the value of the postcode field.
     *
     * @return The patient's postal code
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Setter method for postcode.
     * This method sets the value of the postcode field.
     *
     * @param postcode The patient's postal code
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Getter method for address.
     * This method returns the value of the address field.
     *
     * @return The patient's physical address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for address.
     * This method sets the value of the address field.
     *
     * @param address The patient's physical address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for phone.
     * This method returns the value of the phone field.
     *
     * @return The patient's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for phone.
     * This method sets the value of the phone field.
     *
     * @param phone The patient's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter method for email.
     * This method returns the value of the email field.
     *
     * @return The patient's email contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email.
     * This method sets the value of the email field.
     *
     * @param email The patient's email contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Override the toString method to provide a string representation of the patient.
     * This method returns a formatted string with patient details.
     *
     * @return A string representation of the patient
     */
    @Override
    public String toString() {
        return "Patient [ID=" + patientID + ", Name=" + firstname + " " + surname +
                ", Postcode=" + postcode + ", Address=" + address + ", Phone=" + phone +
                ", Email=" + email + "]";
    }
}