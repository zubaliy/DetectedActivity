package com.athome.detectedactivity;


import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.Date;
import java.util.List;

public class ActivityRecognizedService extends IntentService {

    private static final String TAG = ActivityRecognizedService.class.getSimpleName();

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            switch (activity.getType()) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.d(TAG, "In Vehicle: " + activity.getConfidence());
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.d(TAG, "On Bicycle: " + activity.getConfidence());
                    showNotification(activity, "On Bicycle");
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.d(TAG, "On Foot: " + activity.getConfidence());
                    showNotification(activity, "On Foot");
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.d(TAG, "Running: " + activity.getConfidence());
                    showNotification(activity, "Running");
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.d(TAG, "Still: " + activity.getConfidence());
                    showNotification(activity, "Still");
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.d(TAG, "Tilting: " + activity.getConfidence());
                    showNotification(activity, "Tilting");
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.d(TAG, "Walking: " + activity.getConfidence());
                    showNotification(activity, "Walking");
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.d(TAG, "Unknown: " + activity.getConfidence());
                    showNotification(activity, "Unknown");
                    break;
                }
            }
        }

        Log.d(TAG, " ");

    }

    private void showNotification(DetectedActivity activity, String type) {
        if (activity.getConfidence() >= 75) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentText("Detected: " + type);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(getString(R.string.app_name));
            NotificationManagerCompat.from(this).notify(0, builder.build());
        }
    }

}
