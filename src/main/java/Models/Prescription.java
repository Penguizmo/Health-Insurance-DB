package Models;

import java.util.Date;

/**
 * The Prescription class represents a medical prescription with its details.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the prescription.
 */
public class Prescription {
    // Unique identifier for the prescription.
    private long prescriptionid; // Changed from int to long

    // Date the prescription was prescribed.
    private Date dateprescribed;

    // Dosage of the prescription.
    private String dosage;

    // Duration of the prescription.
    private String duration;

    // Additional comments about the prescription.
    private String comment;

    // Drug ID associated with the prescription.
    private long drugid; // Changed from int to long

    // Doctor ID who prescribed the medication.
    private long doctorid; // Changed from int to long

    // Patient ID for whom the prescription is made.
    private String patientID;

    /**
     * Default constructor.
     */
    public Prescription() {}

    /**
     * Constructor with parameters.
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
    public Prescription(long prescriptionId, Date datePrescribed, String dosage, String duration, String comment, long drugid, long doctorid, String patientID) {
        this.prescriptionid = prescriptionId;
        this.dateprescribed = datePrescribed;
        this.dosage = dosage;
        this.duration = duration;
        this.comment = comment;
        this.drugid = drugid;
        this.doctorid = doctorid;
        this.patientID = patientID;
    }

    // Getters and setters
    public long getPrescriptionId() {
        return prescriptionid;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionid = prescriptionId;
    }

    public Date getDatePrescribed() {
        return dateprescribed;
    }

    public void setDatePrescribed(Date datePrescribed) {
        this.dateprescribed = datePrescribed;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDrugId() {
        return drugid;
    }

    public void setDrugId(long drugid) {
        this.drugid = drugid;
    }

    public long getDoctorId() {
        return doctorid;
    }

    public void setDoctorId(long doctorid) {
        this.doctorid = doctorid;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

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