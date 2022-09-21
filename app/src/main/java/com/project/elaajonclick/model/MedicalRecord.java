package com.project.elaajonclick.model;

import java.util.Date;

public class MedicalRecord {
    private String recordID;
    private Date dateCreation;

    public MedicalRecord(String recordID, Date dateCreation) {
        this.recordID = recordID;
        this.dateCreation = dateCreation;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
