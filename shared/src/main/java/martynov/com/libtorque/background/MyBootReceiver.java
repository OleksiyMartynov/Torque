package martynov.com.libtorque.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBootReceiver extends BroadcastReceiver {
    public MyBootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BootBroadcastReceiver","onReceive");
        Intent i = new Intent(context,MyActivityDetectionIntentService.class);
        i.setAction(MyActivityDetectionIntentService.START_ACTIVITY_RECOGNITION);
        context.startService(i);
    }
}
