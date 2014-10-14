package martynov.com.libtorque;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import martynov.com.libtorque.controllers.MyStatsFragment;


public class MyActivityPhone extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_phone);
        setFragment(new MyStatsFragment());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setFragment(android.support.v4.app.Fragment fragment)
    {
        Log.w("MyPhoneMainActivity", "fragment requested");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainPageFragmentContainer, fragment).commit();

    }
}
