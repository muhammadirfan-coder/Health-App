package com.project.elaajonclick.model.fireStoreApi;

import com.project.elaajonclick.model.Doctor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorHelper {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference doctorRef = db.collection("Doctor");

    public static void addDoctor(String name, String address, String tel, String speciality) {
        Doctor doctor = new Doctor(name, address, tel, FirebaseAuth.getInstance().getCurrentUser().getEmail(), speciality);

        doctorRef.document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).set(doctor);

    }
}
