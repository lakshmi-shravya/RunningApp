package com.example.runningtracker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Repository pulls the needed data from the database
 */
public class MyRepository {
    private RunDao runDao;
    private LiveData<List<Run>> allRuns;
    private LiveData<Integer> count;
    private LiveData<Float> highestDistance;
    private LiveData<Long> highestDuration;

    MyRepository(Application application) {
        MyRoomDataBase db = MyRoomDataBase.getDatabase(application);

        runDao = db.runDao();
        allRuns = runDao.getRuns();
        count = runDao.count();
        highestDistance = runDao.getHighestDistance();
        highestDuration = runDao.getHighestDuration();
    }

    public LiveData<Integer> getCount() {
        return count;
    }

    public LiveData<Float> getHighestDistance() {
        return highestDistance;
    }

    public LiveData<Long> getHighestDuration() {
        return highestDuration;
    }

    LiveData<List<Run>> getAllRuns() {
        return this.allRuns;
    }

    void insert(Run run) {
        MyRoomDataBase.databaseWriteExecutor.execute(() -> {
            runDao.insert(run);
        });
    }

}

