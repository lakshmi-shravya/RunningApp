package com.example.runningtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity to show the user the tracking
 */
public class Tracker extends AppCompatActivity {
    private MyLocationTrackerService.MyBinder myService = null;
    private Button startButton;
    private Button stopButton;

    private long mduration;
    private float mdistance;
    private float mspeed;

    private TextView timeText;
    private TextView distanceText;
    private TextView speedText;

    private Handler postBack = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_recorder);

        this.startService(new Intent(this, MyLocationTrackerService.class));
        this.bindService(new Intent(this, MyLocationTrackerService.class), serviceConnection, Context.BIND_AUTO_CREATE);


        timeText = findViewById(R.id.timeTextView);
        distanceText = findViewById(R.id.distanceTextView);
        speedText = findViewById(R.id.speedTextView);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.endButton);

        stopButton.setEnabled(false);
        startButton.setEnabled(false);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = (MyLocationTrackerService.MyBinder) service;

            if(ContextCompat.checkSelfPermission(Tracker.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                stopButton.setEnabled(false);
                startButton.setEnabled(false);
                return;
            }

            if(myService != null && myService.tracking()) {
                stopButton.setEnabled(true);
                startButton.setEnabled(false);
            } else {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }


            // thread to updating the tracking information
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (myService != null) {
                        float d = (float) myService.getDuration();
                        mduration = (long) d;  // in seconds
                        mdistance = myService.getDistance();
                        //Log.d("comp3018", "distance "+mdistance);
                        long hours = mduration / 3600;
                        long minutes = (mduration % 3600) / 60;
                        long seconds = mduration % 60;

                        mspeed = 0;
                        if(d != 0) {
                            mspeed = mdistance / (d / 3600);
                        }
                        //Log.d("comp3018", "avgSpeed "+mspeed);
                        final String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        final String dist = String.format("%.2f", mdistance);
                        final String avgs = String.format("%.2f", mspeed);
                        //Log.d("comp3018", "duration "+mduration);
                        postBack.post(new Runnable() {
                            @Override
                            public void run() {
                                timeText.setText(time);
                                speedText.setText(avgs);
                                distanceText.setText(dist);
                            }
                        });

                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    };


    public void onClickStartRecording(View view) {
        myService.startRecordingJourney();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void onClickStopRecording(View view) {
        myService.stopRecordingJourney();
//        Log.d("comp3018", "STOP selected");

        startButton.setEnabled(false);
        stopButton.setEnabled(false);
//        Log.d("comp3018", "Congrats you ave stopped recording");

        Intent infoIntent = new Intent(Tracker.this, DisplayInfoAfterRun.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("distance",mdistance);
        bundle.putLong("duration",mduration);
        bundle.putFloat("speed",mspeed);
        infoIntent.putExtras(bundle);
//        Log.d("comp3018", "duration--->"+mduration);
//        Log.d("comp3018","going to DisplayInfoAfterRun Activity");
        startActivity(infoIntent);
    }

}