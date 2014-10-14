package martynov.com.libtorque.controllers;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import martynov.com.libtorque.charts.MyBarChartView;
import martynov.com.libtorque.charts.MyDayChartView;
import martynov.com.libtorque.models.MyActivityData;
import martynov.com.libtorque.models.MyDayData;
import martynov.com.libtorque.storage.MyActivityDatabase;
import martynov.com.libtorque.storage.MyDatabase;
import martynov.com.torque.R;


public class MyPeriodFragment extends Fragment {

    MyBarChartView thisView;
    public MyPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_period, container, false);
        thisView =new MyBarChartView(getActivity());
        return thisView;
    }
    @Override
    public void onStart() {
        super.onStart();
        int weekOffset=getArguments().getInt(MyControllerConstants.WEEK_NUMBER,-1);
        if(weekOffset!=-1)
        {
            MyActivityDatabase db = new MyActivityDatabase(getActivity());
            List<MyDayData> days=db.getWeekDays(weekOffset);

            thisView.setDataList(days);
        }

    }


}
