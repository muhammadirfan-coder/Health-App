package com.project.elaajonclick.controller;

import android.app.Activity;
import android.os.Bundle;

import com.project.elaajonclick.R;

import com.project.elaajonclick.model.adapter.DoctorAppointmentAdapter;
import com.project.elaajonclick.model.AppointmentInformation;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAppointmentActivity extends Activity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference doctorAppointmentRef = db.collection("Doctor");
    private DoctorAppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointement);
        setUpRecyclerView();
    }

    public void setUpRecyclerView() {
        //Get the doctors by patient id
        final String doctorID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query query = doctorAppointmentRef.document("" + doctorID + "")
                .collection("appointmentRequest")
                .orderBy("time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<AppointmentInformation> options = new FirestoreRecyclerOptions.Builder<AppointmentInformation>()
                .setQuery(query, AppointmentInformation.class)
                .build();

        adapter = new DoctorAppointmentAdapter(options);
        //ListMyDoctors
        RecyclerView recyclerView = findViewById(R.id.DoctorAppointement);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
