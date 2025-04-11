package Models;

/**
 * The InsuredPatient class represents a patient with insurance details.
 * This class extends the Patient class and adds an insurance ID field.
 * It provides getter and setter methods for the insurance ID, and a toString method
 * for a string representation of the insured patient.
 */
public class InsuredPatient extends Patient {
    // This field stores the insurance identifier linking the patient to their insurance provider.
    private String insuranceID;

    /**
     * Constructor. This method is called when a new InsuredPatient object is created with specific details.
     * It takes parameters to set the fields of the class.
     *
     * @param patientID   The unique identifier for the patient
     * @param firstname   The patient's first name
     * @param surname     The patient's surname or last name
     * @param postcode    The patient's postal code
     * @param address     The patient's physical address
     * @param phone       The patient's phone number
     * @param email       The patient's email contact
     * @param insuranceID The insurance identifier linking the patient to their insurance provider
     */
    public InsuredPatient(String patientID, String firstname, String surname, String postcode, String address, String phone, String email, String insuranceID) {
        super(patientID, firstname, surname, postcode, address, phone, email);
        this.insuranceID = insuranceID;
    }

    /**
     * Getter method for insuranceId. This method returns the value of the insuranceId field.
     *
     * @return The insurance identifier linking the patient to their insurance provider
     */
    public String getInsuranceId() {
        return insuranceID;
    }

    /**
     * Setter method for insuranceId. This method sets the value of the insuranceId field.
     *
     * @param insuranceId The insurance identifier linking the patient to their insurance provider
     */
    public void setInsuranceId(String insuranceId) {
        this.insuranceID = insuranceId;
    }

    /**
     * Override the toString method to provide a string representation of the insured patient.
     * This method returns a formatted string with insured patient details.
     *
     * @return A string representation of the insured patient
     */
    @Override
    public String toString() {
        return "InsuredPatient [ID=" + getPatientId() + ", Name=" + getFirstName() + " " + getSurname() +
                ", Postcode=" + getPostcode() + ", Address=" + getAddress() + ", Phone=" + getPhone() +
                ", Email=" + getEmail() + ", InsuranceID=" + insuranceID + "]";
    }
}