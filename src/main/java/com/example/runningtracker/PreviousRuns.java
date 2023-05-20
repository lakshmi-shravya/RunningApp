package com.example.runningtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

/**
 * This activity shows all the previous journeys recorded
 * The recycler view is used to present the data
 */
public class PreviousRuns extends AppCompatActivity {
    private RunViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_runs);
//        Log.d("comp3018", "In previous runs ");


        RecyclerView recyclerView = findViewById(R.id.recycler);
        final RunAdapter adapter = new RunAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RunViewModel.class);
        viewModel.getAllRuns().observe(this, runs -> {
            adapter.setData(runs);
        });
    }
}