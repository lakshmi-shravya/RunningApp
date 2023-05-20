package com.example.runningtracker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * ViewModel that is used to access the database through the repository
 */
public class RunViewModel extends AndroidViewModel {
    private MyRepository repository;
    private final LiveData<List<Run>> allRuns;
    private LiveData<Integer> count;
    private LiveData<Float> highestDistance;
    private LiveData<Long> highestDuration;


    public RunViewModel(Application application) {
        super(application);
        repository = new MyRepository(application);
        allRuns = repository.getAllRuns();
        count = repository.getCount();
        highestDistance = repository.getHighestDistance();
        highestDuration = repository.getHighestDuration();
    }
    public LiveData<Integer> getCount() {
        return count;
    }
    LiveData<List<Run>> getAllRuns() { return allRuns; }

    public void insert(Run run) { repository.insert(run); }

    public LiveData<Float> getHighestDistance() {
        return highestDistance;
    }

    public LiveData<Long> getHighestDuration() {
        return highestDuration;
    }
}
