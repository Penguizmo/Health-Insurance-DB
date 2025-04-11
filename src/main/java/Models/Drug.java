package Models;

/**
 * The Drug class represents a drug with its details.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the drug.
 */
public class Drug {
    // Fields to store the drug's information
    private int drugid; // Unique identifier for the drug
    private String drugname; // Name of the drug
    private String sideeffects; // Side effects of the drug
    private String benefits; // Benefits of the drug

    /**
     * Default constructor.
     * This method is called when a new Drug object is created without any details.
     */
    public Drug() {}

    /**
     * Constructor.
     * This method is called when a new Drug object is created with specific details.
     * It takes parameters to set the fields of the class.
     *
     * @param drugid      The unique identifier for the drug
     * @param drugname    The drug's name
     * @param sideeffects The drug's side effects
     * @param benefits    The drug's benefits
     */
    public Drug(int drugid, String drugname, String sideeffects, String benefits) {
        this.drugid = drugid;
        this.drugname = drugname;
        this.sideeffects = sideeffects;
        this.benefits = benefits;
    }

    /**
     * Getter method for drugid.
     * This method returns the value of the drugid field.
     *
     * @return The unique identifier for the drug
     */
    public int getDrugId() {
        return drugid;
    }

    /**
     * Setter method for drugid.
     * This method sets the value of the drugid field.
     *
     * @param drugid The unique identifier for the drug
     */
    public void setDrugId(int drugid) {
        this.drugid = drugid;
    }

    /**
     * Getter method for drugname.
     * This method returns the value of the drugname field.
     *
     * @return The drug's name
     */
    public String getDrugName() {
        return drugname;
    }

    /**
     * Setter method for drugname.
     * This method sets the value of the drugname field.
     *
     * @param drugname The drug's name
     */
    public void setDrugName(String drugname) {
        this.drugname = drugname;
    }

    /**
     * Getter method for sideeffects.
     * This method returns the value of the sideeffects field.
     *
     * @return The drug's side effects
     */
    public String getSideEffects() {
        return sideeffects;
    }

    /**
     * Setter method for sideeffects.
     * This method sets the value of the sideeffects field.
     *
     * @param sideeffects The drug's side effects
     */
    public void setSideEffects(String sideeffects) {
        this.sideeffects = sideeffects;
    }

    /**
     * Getter method for benefits.
     * This method returns the value of the benefits field.
     *
     * @return The drug's benefits
     */
    public String getBenefits() {
        return benefits;
    }

    /**
     * Setter method for benefits.
     * This method sets the value of the benefits field.
     *
     * @param benefits The drug's benefits
     */
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    /**
     * Override the toString method to provide a string representation of the drug.
     * This method returns a formatted string with drug details.
     *
     * @return A string representation of the drug
     */
    @Override
    public String toString() {
        return "Drug [ID=" + drugid + ", Name=" + drugname + ", Side Effects=" + sideeffects + ", Benefits=" + benefits + "]";
    }
}