package com.project.elaajonclick.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class CureType {
    private String medicines;
    private String description;
    private String treatment;
    private String type;
    private Date dateCreated;
    private String doctor;

    public CureType() {

    }

    public CureType(String medicines, String description, String treatment, String type, String doctor) {
        this.medicines = medicines;
        this.description = description;
        this.treatment = treatment;
        this.type = type;
        this.doctor = doctor;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ServerTimestamp
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}

