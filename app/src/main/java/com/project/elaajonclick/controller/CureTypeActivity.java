package com.project.elaajonclick.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.project.elaajonclick.R;

import com.project.elaajonclick.model.CureType;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

public class CureTypeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText medicines;
    private EditText description;
    private EditText treatment;
    private Spinner cureType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cure_type);

        medicines = findViewById(R.id.cure_medicines);
        description = findViewById(R.id.cure_description);
        treatment = findViewById(R.id.cure_treatment);
        cureType = findViewById(R.id.cure_type_spinner);

        //Spinner to choose fiche type
        Spinner spinner = findViewById(R.id.cure_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cure_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Add fiche
        Button addFicheButton = findViewById(R.id.button_add_cure);
        addFicheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCureType();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String SelectedCureType = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void addCureType() {
        String medicinesType = medicines.getText().toString();
        String descriptionType = description.getText().toString();
        String treatmentType = treatment.getText().toString();
        String typeCure = cureType.getSelectedItem().toString();

        String patient_name = getIntent().getStringExtra("patient_name");
        String patient_email = getIntent().getStringExtra("patient_email");


        CollectionReference cureRef = FirebaseFirestore.getInstance().collection("Patient").document("" + patient_email + "")
                .collection("MyMedicalFolder");
        cureRef.document().set(new CureType(medicinesType, descriptionType, treatmentType, typeCure, FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        //cureRef.add(new Fiche(medicinesType, descriptionType, treatmentType, typeCure, FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()));
        Toast.makeText(this, "Cure added." + patient_name, Toast.LENGTH_LONG).show();
        finish();
    }

}
