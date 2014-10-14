package martynov.com.libtorque.charts;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import martynov.com.libtorque.models.MyActivityData;
import martynov.com.libtorque.models.MyDayData;
import martynov.com.libtorque.simple2d.IMyOnTapListener;
import martynov.com.libtorque.simple2d.MyList;
import martynov.com.torque.R;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyBarChartView extends View {
    //private List<MyDayData> dataList;
    private MyList barList;
    private int width,height;

    private static final String ORANGE="#FF7F00";
    private static final String YELLOW="#DBDB5C";
    private static final String GREEN="#A5FF00";
    private static final String BLUE="#00B6FF";
    private static final String PURPLE="#CD66CD";

    public MyBarChartView(Context context) {
        super(context);
    }

    public void setDataList(List<MyDayData> dataList) {
        //this.dataList = dataList;
        barList= new MyList(MyList.MyListType.VERTICAL);

        for(final MyDayData day : dataList) {
            Log.i("bar item","Day:");
            ArrayList<MyBarItem> biList= new ArrayList<MyBarItem>();
            for (MyActivityData item : day.getActivities()) {
                MyBarItem bi = new MyBarItem(item.getCount(), getColorForType(item.getType()));
                Log.i("bar item",bi.toString());
                biList.add(bi);
            }
            MyTappableTitledBar bar = new MyTappableTitledBar(biList,prepDate(day.getDate().toShortString()));
            bar.setOnTapListener(new IMyOnTapListener<MyTappableTitledBar>() {
                @Override
                public void onTap(MyTappableTitledBar sender) {
                    Log.i("test","hello " +day.toString());
                    new AlertDialog.Builder(getContext()).setTitle(prepDate(day.getDate().toShortString()))
                            .setView(new MyDayChartView(getContext(),day.getActivities()))
                            .setIcon(R.drawable.ic_launcher).setNegativeButton("Close",null)
                            .setCancelable(true).show();
                }
            });
            barList.addItem(bar);
        }

    }
    private String prepDate(String date)
    {
        String[] sArr=date.split(" ");
        if(sArr.length>=2)
        {
            return sArr[0]+" "+sArr[1];
        }
        return date;

    }
    private int getColorForType(int type) {
        if(type==0)//vehicle
        {
            return Color.parseColor(PURPLE);
        }
        else if(type==1)//bicycle
        {
            return Color.parseColor(BLUE);
        }
        else if(type==2||type==7||type==8)//on foot, can be walking or running
        {
            return Color.parseColor(GREEN);
        }
        else if(type==3||type==4)//still or unknown
        {
            return Color.parseColor(ORANGE);
        }
        else if(type==5)//tilting
        {
            return Color.parseColor(YELLOW);
        }
        else
        {
            return Color.BLACK;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.parseColor("#696969"));
        RectF viewRect = new RectF(0,0,width,height);
        Log.i("bar rect",viewRect.toString());
        if(barList!=null) {
            barList.onDraw(canvas, viewRect);
            Log.i("test","drew");
        }
        else
        {
            //todo print text
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        this.width = w;
        this.height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getActionMasked()==MotionEvent.ACTION_UP) {
            barList.checkTap((int) event.getX(), (int) event.getY());
        }
        return true;//super.onTouchEvent(event)
    }
}
