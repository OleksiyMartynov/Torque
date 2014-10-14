package martynov.com.libtorque.models;

import java.util.ArrayList;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyGroupedData {
    private String title;
    private ArrayList<Float> data;

    public MyGroupedData(String title, ArrayList<Float> data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Float> getData() {
        return data;
    }
    public float getSum()
    {
        if(data!=null)
        {
            float result=0;
            for(Float f : data)
            {
                result+=f;
            }
            return  result;
        }
        return 0;
    }
}
