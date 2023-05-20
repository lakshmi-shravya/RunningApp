package com.example.runningtracker;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * MyLocationListener keeps track of the changing location during the run
 */
public class MyLocationListener implements LocationListener {
    ArrayList<Location> locationArrayList;
    boolean recordingStarted;

    public MyLocationListener(){
        locationArrayList = new ArrayList<Location>();
        recordingStarted = false;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(recordingStarted){
            locationArrayList.add(location);
        }
    }


    @Override
    public void onProviderEnabled(String provider) {
        Log.d("comp3018", "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("comp3018", "onProviderDisabled: " + provider);
    }

    public float getDistance(){
        if(locationArrayList.size() <= 1) {
            return 0;
        }
        // distance from first location to last recorded location in miles
        Location startingLocation =  locationArrayList.get(0);
        float distanceToChangedLocation = startingLocation.distanceTo(locationArrayList.get(locationArrayList.size() - 1)) / 1609;
        return distanceToChangedLocation;
    }

    public ArrayList<Location> getLocationArrayList() {
        return locationArrayList;
    }

    public boolean isRecordingStarted() {
        return recordingStarted;
    }

    public void setRecordingStarted(boolean recordingStarted) {
        this.recordingStarted = recordingStarted;
    }
}
