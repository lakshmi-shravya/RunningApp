package com.example.runningtracker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "run_table")
public class Run {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int runID;

    @NonNull
    private String date;

    @NonNull
    private long duration;

    private double avgSpeed;

    private double distance;

    private String rating;

    private String notes;

    public Run(@NonNull long duration, double avgSpeed,
               double distance,String rating,String notes,@NonNull String date) {
        this.avgSpeed =avgSpeed;
        this.duration = duration;
        this.distance = distance;
        this.rating = rating;
        this.notes = notes;
        this.date = date;
    }
    @NonNull
    public int getRunID() {
        return runID;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public long getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    @NonNull
    public String getStringDuration() {
        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;

        final String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public String getStringSpeed() {
        final String avgs = String.format("%.2f", this.avgSpeed);
        return avgs;
    }

    public String getStringDistance() {
        final String dist = String.format("%.2f", this.distance);
        return dist;
    }

    public String getRating() { return rating; }

    public String getNotes() { return notes; }

    public void setRunID(int runID) {
        this.runID = runID;
    }

}
