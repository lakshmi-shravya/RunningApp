package com.example.runningtracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RunDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Run run);

    @Query("DELETE FROM run_table")
    void deleteAll();

    @Query("SELECT * FROM run_table ORDER BY runID DESC")
    LiveData<List<Run>> getRuns();

    @Query("SELECT COUNT(*) FROM run_table ")
    LiveData<Integer> count();

    @Query("SELECT COALESCE(MAX(distance),0) FROM run_table")
    LiveData<Float> getHighestDistance();

    @Query("SELECT COALESCE(MAX(duration),0) FROM run_table")
    LiveData<Long> getHighestDuration();
}
