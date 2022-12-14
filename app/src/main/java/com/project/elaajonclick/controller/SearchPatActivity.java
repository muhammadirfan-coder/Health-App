package com.project.elaajonclick.controller;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.project.elaajonclick.R;

import com.project.elaajonclick.model.Doctor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchPatActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference doctorRef = db.collection("Doctor");

    private DoctorAdapterFiltered adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pat);
        configureToolbar();
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.serachPatRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = doctorRef.orderBy("name", Query.Direction.DESCENDING);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                adapter = new DoctorAdapterFiltered(task.getResult().toObjects(Doctor.class));
                recyclerView.setAdapter(adapter);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        Drawable r = getResources().getDrawable(R.drawable.ic_local_hospital_black_24dp);
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" Speciality");
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint(Html.fromHtml("<font color = #000000>" + "Search Doctor" + "</font>"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DoctorAdapterFiltered.specialitySearch = false;
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.option_all:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("");
                return true;
            case R.id.option_general:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("General Physician");
                return true;
            case R.id.option_Dentist:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Dentist");
                return true;
            case R.id.option_cardio:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Cardiologist");
                return true;
            case R.id.option_ophthalmology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Ophthalmology");
                return true;
            case R.id.option_pediatrics:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Pediatrician");
                return true;
            case R.id.option_radio:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Radiologist");
                return true;
            case R.id.option_pathology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Pathologist");
                return true;
            case R.id.option_neurology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Neurologist");
                return true;
            case R.id.option_psychiatrist:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Psychiatrist");
                return true;
            case R.id.option_Gynecology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Gynecologist");
                return true;
            case R.id.option_Urology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Urologist");
                return true;
            case R.id.option_surgeon:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Surgeon");
                return true;
            case R.id.option_dermatology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Dermatologist");
                return true;
            case R.id.option_orthopedics:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Orthopedics");
                return true;
            case R.id.option_nephrology:
                DoctorAdapterFiltered.specialitySearch = true;
                adapter.getFilter().filter("Nephrologist");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle("Doctors list");
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }


}
