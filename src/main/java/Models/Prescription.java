package Models;

import java.util.Date;

/**
 * The Prescription class represents a medical prescription with its details.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the prescription.
 */
public class Prescription {
    // Unique identifier for the prescription.
    private int prescriptionid;

    // Date the prescription was prescribed.
    private Date dateprescribed;

    // Dosage of the prescription.
    private String dosage;

    // Duration of the prescription.
    private String duration;

    // Additional comments about the prescription.
    private String comment;

    // Drug ID associated with the prescription.
    private int drugid;

    // Doctor ID who prescribed the medication.
    private int doctorid;

    // Patient ID for whom the prescription is made.
    private String patientID;

    /**
     * Default constructor.
     * This method is called when a new Prescription object is created without any details.
     */
    public Prescription() {}

    /**
     * Constructor.
     * This method is called when a new Prescription object is created with specific details.
     *
     * @param prescriptionId The unique identifier for the prescription.
     * @param datePrescribed The date the prescription was prescribed.
     * @param dosage         The dosage of the prescription.
     * @param duration       The duration of the prescription.
     * @param comment        Any additional comments about the prescription.
     * @param drugid         The drug ID associated with the prescription.
     * @param doctorid       The doctor ID who prescribed the medication.
     * @param patientID      The patient ID for whom the prescription is made.
     */
    public Prescription(int prescriptionId, Date datePrescribed, String dosage, String duration, String comment, int drugid, int doctorid, String patientID) {
        this.prescriptionid = prescriptionId;
        this.dateprescribed = datePrescribed;
        this.dosage = dosage;
        this.duration = duration;
        this.comment = comment;
        this.drugid = drugid;
        this.doctorid = doctorid;
        this.patientID = patientID;
    }

    /**
     * Getter method for prescription ID.
     *
     * @return The unique identifier for the prescription.
     */
    public int getPrescriptionId() {
        return prescriptionid;
    }

    /**
     * Setter method for prescription ID.
     *
     * @param prescriptionId The unique identifier for the prescription.
     */
    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionid = prescriptionId;
    }

    /**
     * Getter method for date prescribed.
     *
     * @return The date the prescription was prescribed.
     */
    public Date getDatePrescribed() {
        return dateprescribed;
    }

    /**
     * Setter method for date prescribed.
     *
     * @param datePrescribed The date the prescription was prescribed.
     */
    public void setDatePrescribed(Date datePrescribed) {
        this.dateprescribed = datePrescribed;
    }

    /**
     * Getter method for dosage.
     *
     * @return The dosage of the prescription.
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Setter method for dosage.
     *
     * @param dosage The dosage of the prescription.
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Getter method for duration.
     *
     * @return The duration of the prescription.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Setter method for duration.
     *
     * @param duration The duration of the prescription.
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Getter method for comment.
     *
     * @return Additional comments about the prescription.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter method for comment.
     *
     * @param comment Additional comments about the prescription.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter method for drug ID.
     *
     * @return The drug ID associated with the prescription.
     */
    public int getDrugId() {
        return drugid;
    }

    /**
     * Setter method for drug ID.
     *
     * @param drugid The drug ID associated with the prescription.
     */
    public void setDrugId(int drugid) {
        this.drugid = drugid;
    }

    /**
     * Getter method for doctor ID.
     *
     * @return The doctor ID who prescribed the medication.
     */
    public int getDoctorId() {
        return doctorid;
    }

    /**
     * Setter method for doctor ID.
     *
     * @param doctorid The doctor ID who prescribed the medication.
     */
    public void setDoctorId(int doctorid) {
        this.doctorid = doctorid;
    }

    /**
     * Getter method for patient ID.
     *
     * @return The patient ID for whom the prescription is made.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Setter method for patient ID.
     *
     * @param patientID The patient ID for whom the prescription is made.
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Override the toString method to provide a string representation of the prescription.
     *
     * @return A string representation of the prescription.
     */
    @Override
    public String toString() {
        return "Prescription [ID=" + prescriptionid +
                ", Date Prescribed=" + dateprescribed +
                ", Dosage=" + dosage +
                ", Duration=" + duration +
                ", Comment=" + comment +
                ", Drug ID=" + drugid +
                ", Doctor ID=" + doctorid +
                ", Patient ID=" + patientID + "]";
    }
}