package com.project.elaajonclick.model;

public class Patient {
    private String name;
    private String address;
    private String tel;
    private String email;
    private String DOB;
    private String maritalStatus;


    public Patient() {
        //needed for firebase
    }

    public Patient(String name, String address, String tel, String email, String DOB, String maritalStatus) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.DOB = DOB;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
