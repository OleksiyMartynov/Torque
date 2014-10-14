package martynov.com.libtorque.models;

import android.content.Context;

import java.util.List;

import martynov.com.libtorque.storage.MyActivityDatabase;

/**
 * Created by Oleksiy on 7/20/2014.
 */
public class MyDayData
{
    private int id=-1;
    //private String date;
    private MyDate date;
    private Context context;

    public MyDayData(int id, MyDate date, Context context) {
        this.id = id;
        this.date = date;
        this.context=context;
    }

    public int getId() {
        return id;
    }

    public MyDate getDate() {
        return date;
    }
    public List<MyActivityData> getActivities()
    {
        MyActivityDatabase db = new MyActivityDatabase(context);
        List<MyActivityData> data =db.getActivities(id);
        for(MyActivityData a : data)
        {
            a.setDate(date);
        }
        return data;
    }

    @Override
    public String toString() {
        return "MyDayData{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", context=" + context +
                '}';
    }
}
