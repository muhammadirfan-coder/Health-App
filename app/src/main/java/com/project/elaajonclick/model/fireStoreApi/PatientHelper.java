package com.project.elaajonclick.model.fireStoreApi;

import com.project.elaajonclick.model.Patient;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientHelper {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference patientRef = db.collection("Patient");

    public static void addPatient(String name, String address, String tel) {
        Patient patient = new Patient(name, address, tel, FirebaseAuth.getInstance().getCurrentUser().getEmail(), "23/03/2000", "Single");
        System.out.println("Create object patient");
        patientRef.document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).set(patient);
    }
}
