package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ViewTestInfoActivity extends AppCompatActivity {

    public static final String VIEW_TEST = "view_test";
    private static final int TEST_INFO = 99;
    private AppViewModel viewModel;
    private TextView textView;
    private RecyclerView recyclerView;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test_info);

        // view model
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);

        // recyclerView
        recyclerView = findViewById(R.id.recyclerViewTests);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final CustomAdapterForTest adapter = new CustomAdapterForTest();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        // obtain patient info
        Intent intent = getIntent();
        patient = MainActivity.gson.fromJson(intent.getStringExtra(MainActivity.VIEW_PATIENT), Patient.class);
        int patientId = patient.patientId;

        //if the LiveData already has data it will delivered
        // to the observer to set the adpater value (set tests)
        viewModel.getAllTestByPatientIdLiveData(patientId).observe(this, new Observer<List<Test>>() {
            @Override
            public void onChanged(List<Test> result) {
                adapter.setTests(result);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
}