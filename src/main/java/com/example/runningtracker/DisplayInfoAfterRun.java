package com.example.runningtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisplayInfoAfterRun extends AppCompatActivity {
    private float distance;
    private float avgSpeed;
    private long duration;
    EditText ratingText;
    EditText notesText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info_after_run);
//        Log.d("comp3018", "onCreate: In display Info");
        Intent intent = new Intent();
        Bundle bundle = getIntent().getExtras();
        distance = bundle.getFloat("distance");
        avgSpeed = bundle.getFloat("speed");
        duration = bundle.getLong("duration");

        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;

        final String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        final String dist = String.format("%.2f", distance);
        final String avgs = String.format("%.2f", avgSpeed);

        TextView finalTime = findViewById(R.id.finalTime);
        TextView finalDistance = findViewById(R.id.finalDistance);
        TextView finalSpeed = findViewById(R.id.finalSpeed);

        ratingText = findViewById(R.id.editTextRating);
        notesText = findViewById(R.id.editTextNotes);


        finalTime.setText(time);
        finalDistance.setText(dist);
        finalSpeed.setText(avgs);


    }
    public void onClickSave(View view) {
        saveData();
        Intent mainActivityIntent = new Intent(DisplayInfoAfterRun.this, MainActivity.class);
        startActivity(mainActivityIntent);

    }
    public void saveData(){
        RunViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RunViewModel.class);
        String notes = notesText.getText().toString();
        String rating = ratingText.getText().toString();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        viewModel.insert(new Run (duration,avgSpeed,distance,rating,notes,currentDate));
//        Log.d("comp3018", "notes: "+notes);
//        Log.d("comp3018", "rating: "+rating);
//        Log.d("comp3018", "currentDate: "+currentDate);

    }
}