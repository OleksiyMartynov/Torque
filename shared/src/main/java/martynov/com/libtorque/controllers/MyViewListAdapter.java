package martynov.com.libtorque.controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import martynov.com.libtorque.charts.MyDataRowItemView;
import martynov.com.libtorque.models.MyGroupedData;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyViewListAdapter extends ArrayAdapter<MyGroupedData> {
    public Context myContext;
    private List<MyGroupedData> myObjects;
    public MyViewListAdapter(Context context, int resource, List<MyGroupedData> objects) {
        super(context, resource, objects);
        this.myContext=context;
        this.myObjects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        MyGroupedData dataItem = getItem(position);
        MyDataRowItemView view = null;
        if(convertView==null) //first time creating or recreating view
        {
            view = new MyDataRowItemView(myContext);
            convertView = view;
        }
        else //already exists
        {
            view = (MyDataRowItemView)convertView;
        }
        view.setData(dataItem);
        return convertView;
    }
    @Override
    public void remove(MyGroupedData o)
    {
        super.remove(o);
    }
}
