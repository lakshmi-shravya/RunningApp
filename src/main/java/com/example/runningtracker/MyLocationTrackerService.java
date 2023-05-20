package com.example.runningtracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * Service to keep track of the distance and duration of the run
 */
public class MyLocationTrackerService extends Service {
    private final IBinder binder = new MyBinder();
    private long recordedStartTime = 0 ;
    private long recordedElapsedTime = 0 ;

    private final String CHANNEL_ID = "100";
    private final int NOTIFICATION_ID = 001;

    private LocationManager locationManager;
    private MyLocationListener locationListener;

    public class MyBinder extends Binder {

        public float getDistance() { return MyLocationTrackerService.this.getDistance(); }

        public double getDuration() { return MyLocationTrackerService.this.getDuration(); }

        public boolean tracking() {return MyLocationTrackerService.this.tracking();}

        public void startRecordingJourney() { MyLocationTrackerService.this.startRecordingJourney(); }

        public void stopRecordingJourney() { MyLocationTrackerService.this.stopRecordingJourney(); }

    }

    public float getDistance() {
        return locationListener.getDistance();
    }

    /**
     * calculates the duration of the run so far
     * @return
     */
    public double getDuration() {
        if(recordedStartTime == 0) {
            return 0.0;
        }

        long currentTime = SystemClock.elapsedRealtime();

        if(recordedElapsedTime != 0) {
            currentTime = recordedElapsedTime;
        }

        long durationMilli = currentTime - recordedStartTime;
        return durationMilli / 1000.0;
    }

    /**
     * Check if the tracker is still tracking
     * @return
     */
    public boolean tracking() {
        if (recordedStartTime != 0){
            return true;
        }
        return false;
    }

    /**
     * Start tracking the run
     */
    public void startRecordingJourney() {
        buildNotification();
        locationListener.setRecordingStarted(true);
        recordedStartTime = SystemClock.elapsedRealtime();
        recordedElapsedTime = 0;
    }

    /**
     * Remove the notification and reset the variables when the tracking is stopped
     */
    public void stopRecordingJourney() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        stopForeground(true);

        recordedStartTime = 0;
        recordedElapsedTime = SystemClock.elapsedRealtime();
        locationListener.setRecordingStarted(false);
        locationListener.getLocationArrayList().clear();
    }

    /**
     * Build a foreground notification for the service
     */
    public void buildNotification(){
        // create a notification for the service, when the service in created
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //boiler plate code for compatibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "App Currently Tracking";
            String description = "Current Distance:" + String.valueOf(getDistance());
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, Tracker.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        PendingIntent pendingActionIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setContentTitle("RunIT")
                .setContentText("Tracking your Activity ")
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground ,"Return to the tracker",pendingActionIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        startForeground(NOTIFICATION_ID,mBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("comp3018", "service onCreate");

        locationManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5, // minimum time interval between updates
                    5, // minimum distance between updates, in metres
                    locationListener);
        } catch(SecurityException e) {
            Log.d("comp3018", e.toString());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("comp3018", "service onBind");
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent,  flags, startId);
        Log.d("comp3018", "service onStartCommand");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("comp3018", "onDestroy: ");
        locationManager.removeUpdates(locationListener);
        locationListener = null;
        locationManager = null;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        stopForeground(true);
    }
}