package martynov.com.libtorque.controllers;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import martynov.com.libtorque.charts.MyDayChartView;
import martynov.com.libtorque.models.MyActivityData;
import martynov.com.libtorque.models.MyDayData;
import martynov.com.libtorque.storage.MyActivityDatabase;
import martynov.com.torque.R;


public class MyDayFragment extends Fragment {

    MyDayChartView thisView;
    public MyDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        int dayId=getArguments().getInt(MyControllerConstants.DAY_ID_KEY,-1);
        if(dayId!=-1)
        {
            MyActivityDatabase db = new MyActivityDatabase(getActivity());
            MyDayData dayData = db.getDay(dayId);
            List<MyActivityData> data=dayData.getActivities();
            thisView=new MyDayChartView(getActivity(),data);
        }
        else
        {
            return inflater.inflate(R.layout.fragment_my_day, container, false);
        }*/
        thisView =new MyDayChartView(getActivity());
        return thisView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Day Fragment", "onStart");
        int dayId=getArguments().getInt(MyControllerConstants.DAY_ID_KEY,-1);
        Log.i("Day Fragment","dayId:"+dayId);
        if(dayId!=-1)
        {
            MyActivityDatabase db = new MyActivityDatabase(getActivity());
            MyDayData dayData = db.getDay(dayId);
            List<MyActivityData> data=dayData.getActivities();
            thisView.updateData(data);
        }

    }
}
