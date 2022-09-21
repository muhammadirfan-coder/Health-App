package com.project.elaajonclick.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.elaajonclick.R;

import com.project.elaajonclick.model.Common.Common;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.project.elaajonclick.model.Common.Common.convertBloodToInt;

public class PatientInfoActivity extends AppCompatActivity {

    EditText heightBtn;
    EditText weightBtn;
    Spinner bloodTypeSpinner;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        updateBtn = findViewById(R.id.updateInfoBtn);
        heightBtn = findViewById(R.id.heightBtn);
        weightBtn = findViewById(R.id.weightBtn);
        final Spinner specialityList = (Spinner) findViewById(R.id.bloodType);
        ArrayAdapter<CharSequence> adapterSpecialityList = ArrayAdapter.createFromResource(this,
                R.array.blood_spinner, android.R.layout.simple_spinner_item);
        adapterSpecialityList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialityList.setAdapter(adapterSpecialityList);

        String patient_name = getIntent().getStringExtra("patient_name");
        String patient_email = getIntent().getStringExtra("patient_email");

        FirebaseFirestore.getInstance().collection("Patient").document(patient_email).collection("moreInfo")
                .document(patient_email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        weightBtn.setText("" + documentSnapshot.getString("weight"));
                        heightBtn.setText("" + documentSnapshot.getString("height"));
                        if (documentSnapshot.getString("bloodType") != null)
                            specialityList.setSelection(convertBloodToInt(documentSnapshot.getString("bloodType")));
                    }
                });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("height", "" + heightBtn.getText());
                map.put("weight", "" + weightBtn.getText());
                map.put("bloodType", "" + specialityList.getSelectedItem().toString());
                Log.e("tag", "onClick: " + specialityList.getTag());
                FirebaseFirestore.getInstance().collection("Patient").document(patient_email).collection("moreInfo")
                        .document(patient_email).set(map);
                Toast.makeText(PatientInfoActivity.this, "Update Success!", Toast.LENGTH_SHORT).show();

            }
        });
        if (Common.CurrentUserType.equals("doctor")) {
            updateBtn.setVisibility(View.GONE);
            heightBtn.setEnabled(false);
            weightBtn.setEnabled(false);
            specialityList.setEnabled(false);
        }
    }

}
