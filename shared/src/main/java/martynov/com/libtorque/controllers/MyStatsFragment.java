package martynov.com.libtorque.controllers;




import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Random;

import martynov.com.libtorque.background.MyActivityDetectionIntentService;
import martynov.com.libtorque.models.MyActivityData;
import martynov.com.libtorque.models.MyDate;
import martynov.com.libtorque.models.MyDayData;
import martynov.com.libtorque.storage.MyActivityDatabase;
import martynov.com.torque.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MyStatsFragment extends android.support.v4.app.Fragment {

    static final int NUM_ITEMS = 2;
    View thisView;
    private MyPagerAdapter pagerAdapter;
    private ViewPager pagerView;
    private static final int DAY_PAGE_INDEX=0;
    private static final int WEEK_PAGE_INDEX=1;
    public MyStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisView= inflater.inflate(R.layout.fragment_my_stats, container, false);
        //dissable rotation because it causes problems for charts
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent i = new Intent(getActivity(), MyActivityDetectionIntentService.class);
        i.setAction(MyActivityDetectionIntentService.START_ACTIVITY_RECOGNITION);
        getActivity().startService(i);
        //testData();


        return thisView;
    }

    private void setTabs() {
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                switch (tab.getPosition())
                {
                    case DAY_PAGE_INDEX:
                        pagerView.setCurrentItem(DAY_PAGE_INDEX,true);
                        break;
                    case WEEK_PAGE_INDEX:
                        pagerView.setCurrentItem(WEEK_PAGE_INDEX,true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
        bar.removeAllTabs();
        bar.addTab(bar.newTab().setText("Today").setTabListener(tabListener),DAY_PAGE_INDEX,true);
        bar.addTab(bar.newTab().setText("Week").setTabListener(tabListener),WEEK_PAGE_INDEX);

    }

    private void setPagerView() {
        pagerAdapter = new MyPagerAdapter(getChildFragmentManager());//getActivity().getSupportFragmentManager()
        pagerView = (ViewPager)thisView.findViewById(R.id.pager);
        pagerView.setAdapter(pagerAdapter);
        pagerView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ActionBar bar = getActivity().getActionBar();
                bar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        setPagerView();
        setTabs();
        Log.i("Stats Fragment","onStart");
    }


/*
    private void testData() {
        MyActivityDatabase db = new MyActivityDatabase(getActivity());
        List<MyDayData> days = db.getWeekDays(0);
        Log.i("t", "before writes");
        printTest(days);

        Random r = new Random();
        for (int i = 1; i < 2; i++) {
            Log.i("t", "write #" + i);
            for (int j = 0; j < 50; j++) {
                int rType = r.nextInt(6);
                db.saveActivity(rType, "Jan " + i + " 1999");
            }
        }
        days = db.getWeekDays(0);
        Log.i("t", "after writes");
        printTest(days);

    }*/

    private void printTest(List<MyDayData> days) {
        for (MyDayData day : days) {
            Log.i("Test", day.toString());
            List<MyActivityData> activities = day.getActivities();
            for (MyActivityData activity : activities) {
                Log.i("test", activity.toString());
            }
        }
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if(position==DAY_PAGE_INDEX)
            {
                Log.i("PagerAdapter","fragment #1 requested");
                android.support.v4.app.Fragment f = new MyDayFragment();
                Bundle bundle = new Bundle();
                MyActivityDatabase db = new MyActivityDatabase(MyStatsFragment.this.getActivity());
                bundle.putInt(MyControllerConstants.DAY_ID_KEY,db.dayExists(MyDate.today()));
                f.setArguments(bundle);
                return f;
            }
            else if(position==WEEK_PAGE_INDEX)
            {
                android.support.v4.app.Fragment f = new MyPeriodFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(MyControllerConstants.WEEK_NUMBER,0);
                f.setArguments(bundle);
                return f;
            }
            return  null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

}
