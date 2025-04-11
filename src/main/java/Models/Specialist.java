package Models;

/**
 * The Specialist class extends the Doctor class and adds specialization and experience fields.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the specialist.
 */
public class Specialist extends Doctor {
    // This field stores the specialist's area of specialization.
    private String specialization;

    // This field stores the specialist's years of experience.
    private String experience;

    /**
     * Default constructor.
     * This method is called when a new Specialist object is created without any details.
     */
    public Specialist() {}

    /**
     * Constructor.
     * This method is called when a new Specialist object is created with specific details.
     * It takes parameters to set the fields of the class.
     *
     * @param doctorid       The unique identifier for the doctor
     * @param firstname      The doctor's first name
     * @param surname        The doctor's surname or last name
     * @param address        The doctor's physical address
     * @param email          The doctor's email contact
     * @param hospital       The name of the hospital where the doctor is affiliated
     * @param specialization The specialist's area of specialization
     * @param experience     The specialist's years of experience
     */
    public Specialist(int doctorid, String firstname, String surname, String address, String email, String hospital, String specialization, String experience) {
        super(doctorid, firstname, surname, address, email, hospital);
        this.specialization = specialization;
        this.experience = experience;
    }

    /**
     * Getter method for specialization.
     * This method returns the value of the specialization field.
     *
     * @return The specialist's area of specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Setter method for specialization.
     * This method sets the value of the specialization field.
     *
     * @param specialization The specialist's area of specialization
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Getter method for experience.
     * This method returns the value of the experience field.
     *
     * @return The specialist's years of experience
     */
    public String getExperience() {
        return experience;
    }

    /**
     * Setter method for experience.
     * This method sets the value of the experience field.
     *
     * @param experience The specialist's years of experience
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * Override the toString method to provide a string representation of the specialist.
     * This method returns a formatted string with specialist details.
     *
     * @return A string representation of the specialist
     */
    @Override
    public String toString() {
        return "Specialist [ID=" + getDoctorId() + ", FirstName=" + getFirstName() + ", Surname=" + getSurname() +
                ", Address=" + getAddress() + ", Email=" + getEmail() + ", Hospital=" + getHospital() +
                ", Specialization=" + specialization + ", Experience=" + experience + "]";
    }
}