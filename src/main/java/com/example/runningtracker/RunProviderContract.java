package com.example.runningtracker;

import android.net.Uri;

public class RunProviderContract {
    public static final String AUTHORITY = "com.example.runningtracker.RunContentProvider";

    public static final Uri RUN_URI = Uri.parse("content://"+AUTHORITY+"/run_table");
    public static final Uri RUN_DIST_URI = Uri.parse("content://"+AUTHORITY+"/run_table/distance");
    public static final Uri RUN_DUR_URI = Uri.parse("content://"+AUTHORITY+"/run_table/duration");
    public static final Uri ALL_URI = Uri.parse("content://"+AUTHORITY+"/");

    public static final String RUN_ID = "runID";

    public static final String DISTANCE = "distance";
    public static final String DURATION = "duration";
    public static final String AVGSPEED = "avgSpeed";
    public static final String NOTES = "notes";
    public static final String RATING = "rating";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/RunContentProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/RunContentProvider.data.text";

}
