package com.example.runningtracker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Run.class}, version = 2, exportSchema = false)
public abstract class MyRoomDataBase extends RoomDatabase {
    public abstract RunDao runDao();
    public static SupportSQLiteOpenHelper openHelper;
    private static volatile MyRoomDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDataBase.class, "run_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(createCallback)
                            .build();
                    openHelper = INSTANCE.getOpenHelper();
                }
            }
        }
        return INSTANCE;
    }

    static SupportSQLiteOpenHelper getHelper(){

        return openHelper;
    }

    private static RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("comp3018", "In create call back");
        }
    };
}
