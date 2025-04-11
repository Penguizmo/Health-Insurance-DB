package Models;

import java.time.LocalDate;

/**
 * The Visit class represents a medical visit with its details.
 * This class provides getter and setter methods for each field, and a toString method
 * for a string representation of the visit.
 */
public class Visit {
    // ID of the patient who was examined.
    private String patientID;

    // ID of the doctor who conducted the visit.
    private int doctorid;

    // Date of the visit.
    private LocalDate dateofvisit;

    // Symptoms reported by the patient.
    private String symptoms;

    // Diagnosis provided by the doctor.
    private String diagnosisid;

    /**
     * Default constructor.
     * This method is called when a new Visit object is created without any details.
     */
    public Visit() {}

    /**
     * Constructor.
     * This method is called when a new Visit object is created with specific details.
     *
     * @param patientID   The ID of the patient who was examined.
     * @param doctorid    The ID of the doctor who conducted the visit.
     * @param dateofvisit The date of the visit.
     * @param symptoms    The symptoms reported by the patient.
     * @param diagnosisid The diagnosis provided by the doctor.
     */
    public Visit(String patientID, int doctorid, LocalDate dateofvisit, String symptoms, String diagnosisid) {
        this.patientID = patientID;
        this.doctorid = doctorid;
        this.dateofvisit = dateofvisit;
        this.symptoms = symptoms;
        this.diagnosisid = diagnosisid;
    }

    /**
     * Getter method for patient ID.
     *
     * @return The ID of the patient who was examined.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Setter method for patient ID.
     *
     * @param patientID The ID of the patient who was examined.
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Getter method for doctor ID.
     *
     * @return The ID of the doctor who conducted the visit.
     */
    public int getDoctorid() {
        return doctorid;
    }

    /**
     * Setter method for doctor ID.
     *
     * @param doctorid The ID of the doctor who conducted the visit.
     */
    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    /**
     * Getter method for date of visit.
     *
     * @return The date of the visit.
     */
    public LocalDate getDateofvisit() {
        return dateofvisit;
    }

    /**
     * Setter method for date of visit.
     *
     * @param dateofvisit The date of the visit.
     */
    public void setDateofvisit(LocalDate dateofvisit) {
        this.dateofvisit = dateofvisit;
    }

    /**
     * Getter method for symptoms.
     *
     * @return The symptoms reported by the patient.
     */
    public String getSymptoms() {
        return symptoms;
    }

    /**
     * Setter method for symptoms.
     *
     * @param symptoms The symptoms reported by the patient.
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * Getter method for diagnosis ID.
     *
     * @return The diagnosis provided by the doctor.
     */
    public String getDiagnosisid() {
        return diagnosisid;
    }

    /**
     * Setter method for diagnosis ID.
     *
     * @param diagnosisid The diagnosis provided by the doctor.
     */
    public void setDiagnosisid(String diagnosisid) {
        this.diagnosisid = diagnosisid;
    }

    /**
     * Override the toString method to provide a string representation of the visit.
     *
     * @return A string representation of the visit.
     */
    @Override
    public String toString() {
        return "Visit [PatientID=" + patientID + ", Doctorid=" + doctorid + ", Dateofvisit=" + dateofvisit +
                ", Symptoms=" + symptoms + ", Diagnosisid=" + diagnosisid + "]";
    }
}