package com.project.elaajonclick.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.project.elaajonclick.R;

import com.project.elaajonclick.model.fireStoreApi.DoctorHelper;
import com.project.elaajonclick.model.fireStoreApi.PatientHelper;
import com.project.elaajonclick.model.fireStoreApi.UserHelper;

import static android.widget.AdapterView.*;

public class FirstSignInActivity extends AppCompatActivity {
    private static final String TAG = "FirstSignInActivity";
    private EditText fullName;
    private EditText birthday;
    private EditText teL;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_signin);
        btn = (Button) findViewById(R.id.confirmeBtn);
        fullName = (EditText) findViewById(R.id.firstSignFullName);
        birthday = (EditText) findViewById(R.id.firstSignBirthDay);
        teL = (EditText) findViewById(R.id.firstSignTel);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Spinner specialityList = (Spinner) findViewById(R.id.specialite_spinner);
        ArrayAdapter<CharSequence> adapterSpecialityList = ArrayAdapter.createFromResource(this,
                R.array.specialization_spinner, android.R.layout.simple_spinner_item);
        adapterSpecialityList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialityList.setAdapter(adapterSpecialityList);
        String newAccountType = spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = spinner.getSelectedItem().toString();
                Log.e(TAG, "onItemSelected:" + selected);
                if (selected.equals("Doctor")) {
                    specialityList.setVisibility(View.VISIBLE);
                } else {
                    specialityList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                specialityList.setVisibility(View.GONE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName, birtDay, tel, type, speciality;
                fullName = FirstSignInActivity.this.fullName.getText().toString();
                birtDay = birthday.getText().toString();
                tel = teL.getText().toString();
                type = spinner.getSelectedItem().toString();
                speciality = specialityList.getSelectedItem().toString();
                UserHelper.addUser(fullName, birtDay, tel, type);
                if (type.equals("Patient")) {
                    PatientHelper.addPatient(fullName, "address", tel);
                    System.out.println("Add patient " + fullName + " to patient collection");

                } else {
                    DoctorHelper.addDoctor(fullName, "address", tel, speciality);
                }
                Intent k = new Intent(FirstSignInActivity.this, MainActivity.class);
                startActivity(k);
            }


        });
    }

}
