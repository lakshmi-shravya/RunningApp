package com.example.runningtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    public void onClickRecord(View v) {
        // go to the record run activity
        Intent tracker = new Intent(MainActivity.this, Tracker.class);
        Log.d("comp3018","going to JourneyRecorder Activty");
        startActivity(tracker);
    }

    public void onClickPreviousRuns(View v) {
        // go to the previous runs activity
        Intent previous = new Intent(MainActivity.this, PreviousRuns.class);
        Log.d("comp3018","going to PreviousRuns Activty");
        startActivity(previous);
    }

    public void setView() {

        TextView highestDistance = findViewById(R.id.highestDistance);
        TextView highestDuration = findViewById(R.id.longestDuration);
        TextView totalRuns = findViewById(R.id.allRuns);

        RunViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RunViewModel.class);

        // Create the observer which updates the UI.
        final Observer<Integer> countObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newCount) {
                // Update the UI, in this case, a TextView.
                totalRuns.setText(String.valueOf(newCount));
//                Log.d("comp3018", "text: "+newCount);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.getCount().observe(this, countObserver);

        final Observer<Float> highestDistObserver = new Observer<Float>() {
            @Override
            public void onChanged(@Nullable final Float newDist) {
                final String dist = String.format("%.2f", newDist);
                highestDistance.setText(dist);
            }
        };

        viewModel.getHighestDistance().observe(this, highestDistObserver);

        final Observer<Long> highestDurObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long newDur) {
                long hours = newDur / 3600;
                long minutes = (newDur % 3600) / 60;
                long seconds = newDur % 60;
                final String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                highestDuration.setText(time);
            }
        };

        viewModel.getHighestDuration().observe(this, highestDurObserver);
    }
}