package Models;

/**
 * The Insurance class represents an insurance company with its details.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the insurance.
 */
public class Insurance {
    // Fields to store the insurance company's information
    private String insuranceID; // Unique identifier for the insurance
    private String company; // Name of the insurance company
    private String address; // Address of the insurance company
    private String phone; // Phone number of the insurance company

    /**
     * Default constructor.
     * This method is called when a new Insurance object is created without any details.
     */
    public Insurance() {}

    /**
     * Constructor.
     * This method is called when a new Insurance object is created with specific details.
     * It takes parameters to set the fields of the class.
     *
     * @param insuranceID The unique identifier for the insurance
     * @param company     The insurance company's name
     * @param address     The insurance company's address
     * @param phone       The insurance company's phone number
     */
    public Insurance(String insuranceID, String company, String address, String phone) {
        this.insuranceID = insuranceID;
        this.company = company;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Getter method for insuranceID.
     * This method returns the value of the insuranceID field.
     *
     * @return The unique identifier for the insurance
     */
    public String getInsuranceId() {
        return insuranceID;
    }

    /**
     * Setter method for insuranceID.
     * This method sets the value of the insuranceID field.
     *
     * @param insuranceID The unique identifier for the insurance
     */
    public void setInsuranceId(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    /**
     * Getter method for company.
     * This method returns the value of the company field.
     *
     * @return The insurance company's name
     */
    public String getCompany() {
        return company;
    }

    /**
     * Setter method for company.
     * This method sets the value of the company field.
     *
     * @param company The insurance company's name
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Getter method for address.
     * This method returns the value of the address field.
     *
     * @return The insurance company's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for address.
     * This method sets the value of the address field.
     *
     * @param address The insurance company's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for phone.
     * This method returns the value of the phone field.
     *
     * @return The insurance company's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for phone.
     * This method sets the value of the phone field.
     *
     * @param phone The insurance company's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Override the toString method to provide a string representation of the insurance.
     * This method returns a formatted string with insurance details.
     *
     * @return A string representation of the insurance
     */
    @Override
    public String toString() {
        return "Insurance [ID=" + insuranceID + ", Company=" + company + ", Address=" + address + ", Phone=" + phone + "]";
    }
}