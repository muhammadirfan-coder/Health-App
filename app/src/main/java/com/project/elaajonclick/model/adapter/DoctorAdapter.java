package com.project.elaajonclick.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.elaajonclick.model.Common.Common;

import com.project.elaajonclick.R;

import com.project.elaajonclick.controller.TestActivity;
import com.project.elaajonclick.model.Doctor;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DoctorAdapter extends FirestoreRecyclerAdapter<Doctor, DoctorAdapter.DoctorHolder> {
    static String doc;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference addRequest = db.collection("Request");

    public DoctorAdapter(@NonNull FirestoreRecyclerOptions<Doctor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final DoctorHolder doctorHolder, int i, @NonNull final Doctor doctor) {
        final TextView t = doctorHolder.title;
        doctorHolder.title.setText(doctor.getName());
        doctorHolder.speciality.setText("Speciality : " + doctor.getSpeciality());
        final String idPat = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String idDoc = doctor.getEmail();
        // doctorHolder.image.setImageURI(Uri.parse("drawable-v24/ic_launcher_foreground.xml"));
        doctorHolder.addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> note = new HashMap<>();
                note.put("id_patient", idPat);
                note.put("id_doctor", idDoc);
                addRequest.document().set(note)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(t, "Request Sent", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                doctorHolder.addDoc.setVisibility(View.INVISIBLE);
            }
        });
        doctorHolder.appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc = doctor.getEmail();
                Common.currentDoctor = doctor.getEmail();
                openPage(v.getContext());

            }
        });

    }


    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,
                parent, false);
        return new DoctorHolder(v);
    }


    class DoctorHolder extends RecyclerView.ViewHolder {
        Button appointmentBtn;
        TextView title;
        TextView speciality;
        ImageView image;
        Button addDoc;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            addDoc = itemView.findViewById(R.id.addDocBtn);
            title = itemView.findViewById(R.id.doctor_view_title);
            speciality = itemView.findViewById(R.id.text_view_description);
            image = itemView.findViewById(R.id.doctor_item_image);
            appointmentBtn = itemView.findViewById(R.id.appointemenBtn);
        }
    }

    private void openPage(Context wf) {
        Intent i = new Intent(wf, TestActivity.class);
        wf.startActivity(i);
    }


}
