package com.example.runningtracker;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class RunContentProvider extends ContentProvider {
    private SupportSQLiteOpenHelper dbHelper = null;

    private static final UriMatcher uriMatcher;
    //urimatcher to map the uri to a query using their codes
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RunProviderContract.AUTHORITY, "run_table", 1);
        uriMatcher.addURI(RunProviderContract.AUTHORITY, "run_table/#", 2);
        uriMatcher.addURI(RunProviderContract.AUTHORITY, "run_table/distance", 3);
        uriMatcher.addURI(RunProviderContract.AUTHORITY, "run_table/duration", 4);
        uriMatcher.addURI(RunProviderContract.AUTHORITY, "*", 7);
    }

    @Override
    public boolean onCreate() {

        Log.d("comp3018", "contentprovider oncreate");
        this.dbHelper = MyRoomDataBase.getDatabase(getContext()).getOpenHelper();
        return true;
    }

    @Override
    public String getType(Uri uri) {

        String contentType;

        if (uri.getLastPathSegment() == null) {
            contentType = RunProviderContract.CONTENT_TYPE_MULTIPLE;
        } else {
            contentType = RunProviderContract.CONTENT_TYPE_SINGLE;
        }

        return contentType;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SupportSQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;
        tableName = "run_table";

        long id = db.insert(tableName, 0, values);
        Uri nu = ContentUris.withAppendedId(uri, id);

        Log.d("comp3018", nu.toString());

        getContext().getContentResolver().notifyChange(nu, null);

        return nu;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d("g53mdp", uri.toString() + " " + uriMatcher.match(uri));

        SupportSQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case 1:
                String q1 = "SELECT distance, duration, avgSpeed,notes,rating FROM run_table";
                return db.query(q1, selectionArgs);
            case 2:
                String q2 = "SELECT * FROM run_table ";
                return db.query(q2, selectionArgs);
            case 3:
                String q3 = "SELECT COALESCE(MAX(distance),0) FROM run_table ";
                Log.d("comp3018", "query: 3");
                return db.query(q3, selectionArgs);
            case 4:
                String q4 = "SELECT COALESCE(MAX(duration),0) FROM run_table ";
                return db.query(q4, selectionArgs);
            case 5:
                String q5 = "SELECT COUNT(runID) FROM run_table ";
                return db.query(q5, selectionArgs);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }
}
