package martynov.com.libtorque.background;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import martynov.com.libtorque.storage.MyActivityDatabase;


public class MyActivityDetectionIntentService extends IntentService implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int DETECTION_INTERVAL_SECONDS = 60;
    public static final int DETECTION_INTERVAL_MILLISECONDS = MILLISECONDS_PER_SECOND * DETECTION_INTERVAL_SECONDS;
    public static final int MIN_CONFIDENCE =50;
    private static PendingIntent activityRecognitionPendingIntent;
    private static ActivityRecognitionClient activityRecognitionClient;

    public static final String START_ACTIVITY_RECOGNITION = "start_activity_recognition";
    public static final String STOP_ACTIVITY_RECOGNITION = "stop_activity_recognition";
    public static final String TAG= "MyActivityDetectionIntentService";

    private static boolean isConnected = false;

    public MyActivityDetectionIntentService() {
        super("MyActivityDetectionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.i(TAG,"onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (START_ACTIVITY_RECOGNITION.equals(action)) {
                Log.i(TAG,"intent:"+START_ACTIVITY_RECOGNITION);
                startActivityRecognition();
            } else if (STOP_ACTIVITY_RECOGNITION.equals(action)) {
                Log.i(TAG,"intent:"+STOP_ACTIVITY_RECOGNITION);
                stopActivityRecognition();
            }
            else
            {
                //Log.i(TAG,"intent: other");
                if (ActivityRecognitionResult.hasResult(intent)) {
                    //Log.i(TAG,"intent: activity recognition");
                    ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
                    DetectedActivity activity= result.getMostProbableActivity();
                    if(activity.getConfidence()>MIN_CONFIDENCE)
                    {
                        saveActivity(activity);
                    }
                }
            }
        }
    }
    private void saveActivity(DetectedActivity activity)
    {
        Log.i(TAG, "will save activity type of:"+activity.getType());
        MyActivityDatabase db = new MyActivityDatabase(getBaseContext());
        db.saveActivity(activity);
    }
    public void startActivityRecognition()
    {
        if(!isConnected) {
            activityRecognitionClient = new ActivityRecognitionClient(getBaseContext(), this, this);
            activityRecognitionClient.connect();
        }
    }
    public void stopActivityRecognition()
    {
        if(isConnected) {
            activityRecognitionClient.removeActivityUpdates(activityRecognitionPendingIntent);
            activityRecognitionClient.disconnect();
        }

    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG,"onConnected");
        isConnected=true;
        Intent intent = new Intent(getBaseContext(),MyActivityDetectionIntentService.class);
        activityRecognitionPendingIntent= PendingIntent.getService(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        activityRecognitionClient.requestActivityUpdates(DETECTION_INTERVAL_MILLISECONDS,activityRecognitionPendingIntent);
    }

    @Override
    public void onDisconnected() {
        Log.i(TAG,"onDisconnected");
        isConnected=false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG,"onConnectionFailed");
        isConnected=false;
    }
}
