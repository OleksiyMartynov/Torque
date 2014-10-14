package martynov.com.libtorque;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import martynov.com.libtorque.controllers.MyStatsFragment;

public class MyActivityWatch extends FragmentActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_watch);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);
                setFragment(new MyStatsFragment());
            }
        });
    }
    private void setFragment(android.support.v4.app.Fragment fragment)
    {
        Log.w("MyPhoneMainActivity", "fragment requested");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainWatchPageFragmentContainer, fragment).commit();

    }
}
