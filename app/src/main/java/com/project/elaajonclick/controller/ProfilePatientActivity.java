package com.project.elaajonclick.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.elaajonclick.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dmax.dialog.SpotsDialog;


public class ProfilePatientActivity extends AppCompatActivity {
    private MaterialTextView patientName;
    private MaterialTextView patientPhone;
    private MaterialTextView patientEmail;
    private MaterialTextView patientAddress;
    private MaterialTextView patientAbout;
    private ImageView patientImage;
    StorageReference pathReference;
    final String patientID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference patRef = db.collection("Patient").document("" + patientID + "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);

        patientName = findViewById(R.id.doctor_name);
        patientPhone = findViewById(R.id.doctor_phone);
        patientEmail = findViewById(R.id.doctor_email);
        patientAddress = findViewById(R.id.doctor_address);
        patientAbout = findViewById(R.id.doctor_about);
        patientImage = findViewById(R.id.imageView3);
        Drawable defaultImage = getResources().getDrawable(R.drawable.ic_anon_user_48dp); //default user image
        AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setCancelable(true).build();
        dialog.show();


        //display profile image
        String imageId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        pathReference = FirebaseStorage.getInstance().getReference().child("DoctorProfile/" + imageId + ".jpg");
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ProfilePatientActivity.this)
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(patientImage);
                dialog.dismiss();
                // profileImage.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        patRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                patientName.setText(documentSnapshot.getString("name"));
                patientPhone.setText(documentSnapshot.getString("tel"));
                patientEmail.setText(documentSnapshot.getString("email"));
                patientAddress.setText(documentSnapshot.getString("address"));
                patientImage.setImageDrawable(defaultImage);
            }
        });
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.back:
                //If this activity started from other activity
                finish();
                startHomeActivity();
                return true;

            case R.id.edit:
                //If the edit button is clicked.
                startEditActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startEditActivity() {
        Intent intent = new Intent(this, EditProfilePatientActivity.class);
        intent.putExtra("CURRENT_NAME", patientName.getText().toString());
        intent.putExtra("CURRENT_PHONE", patientPhone.getText().toString());
        intent.putExtra("CURRENT_ADDRESS", patientAddress.getText().toString());
        startActivity(intent);
        finish();
    }
}
