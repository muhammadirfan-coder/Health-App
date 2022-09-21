package com.project.elaajonclick.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.elaajonclick.R;
import com.project.elaajonclick.model.Doctor;
import com.project.elaajonclick.model.Patient;
import com.project.elaajonclick.model.Request;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static androidx.core.content.ContextCompat.startActivities;

public class PatientRequestAdapter extends FirestoreRecyclerAdapter<Request, PatientRequestAdapter.PatientRequestHolder> {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference addRequest = db.collection("Request");

    public PatientRequestAdapter(@NonNull FirestoreRecyclerOptions<Request> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PatientRequestHolder RequestHolder, final int i, @NonNull final Request request) {
        final TextView t = RequestHolder.title;
        final String idPat = request.getId_patient();
        final String idDoc = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String HourPath = request.getHour_path();

        db.collection("Doctor").document(idDoc).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final Doctor onlineDoc = documentSnapshot.toObject(Doctor.class);
                db.collection("Patient").document(idPat).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final Patient pat = documentSnapshot.toObject(Patient.class);
                        RequestHolder.title.setText(pat.getName());
                        RequestHolder.speciality.setText("Want to be your patient");
                        RequestHolder.addDoc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                db.collection("Patient").document(idPat).collection("MyDoctors").document(idDoc).set(onlineDoc);
                                db.collection("Doctor").document(idDoc + "").collection("MyPatients").document(idPat).set(pat);
                                addRequest.whereEqualTo("id_doctor", idDoc + "").whereEqualTo("id_patient", idPat + "").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                                            addRequest.document(documentSnapshot1.getId()).delete();

                                        }
                                    }
                                });
                                db.document(HourPath).update("chosen", "true");
                                Snackbar.make(t, "Patient added", Snackbar.LENGTH_SHORT).show();
                                RequestHolder.addDoc.setVisibility(View.INVISIBLE);

                            }
                        });
                    }
                });
            }
        });


    }

    public void deleteItem(int position) {
        String hour = getSnapshots().getSnapshot(position).getString("hour_path");
        db.document(hour).delete();
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public PatientRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pat_request_item,
                parent, false);
        return new PatientRequestHolder(v);
    }

    class PatientRequestHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView speciality;
        ImageView image;
        Button addDoc;

        public PatientRequestHolder(@NonNull View itemView) {
            super(itemView);
            addDoc = itemView.findViewById(R.id.pat_request_accept_btn);
            title = itemView.findViewById(R.id.pat_request_title);
            speciality = itemView.findViewById(R.id.pat_request_description);
            image = itemView.findViewById(R.id.pat_request_item_image);

        }
    }
}
