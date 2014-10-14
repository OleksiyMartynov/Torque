package martynov.com.libtorque.charts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import martynov.com.libtorque.models.MyActivityData;
import martynov.com.libtorque.simple2d.IMyDrawable;
import martynov.com.libtorque.simple2d.IMyOnTapListener;
import martynov.com.libtorque.simple2d.IMyOnToggleListener;
import martynov.com.libtorque.simple2d.MyList;
import martynov.com.libtorque.simple2d.MyButton;
import martynov.com.libtorque.simple2d.MyToggleButton;
import martynov.com.torque.R;

/**
 * Created by Oleksiy on 7/22/2014.
 */
public class MyDayChartView extends View  {
    private ArrayList<MyPieItem> pieItems;
    List<MyActivityData> dataList;
    private MyList legendItems ;
    private MyPieChart pChart;
    private int width,height;
    private static final String ORANGE="#FF7F00";
    private static final String YELLOW="#DBDB5C";
    private static final String GREEN="#A5FF00";
    private static final String BLUE="#00B6FF";
    private static final String PURPLE="#CD66CD";
    private ArrayList<MyPair<MyPieItem,IMyDrawable>> pairList;
    public MyDayChartView(Context context,List<MyActivityData> dl) {
        super(context);
        updateData(dl);
    }

    public MyDayChartView(Context context) {
        super(context);
    }

    public void updateData(List<MyActivityData> dl)
    {
        pairList=new ArrayList<MyPair<MyPieItem, IMyDrawable>>();
        this.dataList=dl;
        pieItems = new ArrayList<MyPieItem>();
        ArrayList<IMyDrawable> legendList=new ArrayList<IMyDrawable>();
        for(MyActivityData activity: dataList)
        {
            MyPair<MyPieItem,IMyDrawable> pair = createChartItemAndLegendItemPair(activity);
            pairList.add(pair);
            if(pair!=null) {
                pieItems.add(pair.getOne());
                legendList.add(pair.getTwo());
            }
        }
        pChart=new MyPieChart(pieItems);
        legendItems = new MyList(MyList.MyListType.HORIZONTAL);
        legendItems.setItems(legendList);
        legendItems.setSquareItems(true);
    }
    private MyPair<MyPieItem,IMyDrawable> createChartItemAndLegendItemPair(MyActivityData activity)
    {
        Log.i("create item","type "+activity.getType());
        Resources r =getResources();
        float bw=4.0f;
        int type = activity.getType();
        if(type==0)//vehicle
        {
            Log.i("create item","color "+Color.parseColor(PURPLE));
            MyToggleButton tb =new MyToggleButton(r,R.drawable.ic_vehicle,Color.parseColor(PURPLE));
            tb.setBorderWidth(bw);
            MyPieItem pi =new MyPieItem(activity.getCount(),Color.parseColor(PURPLE));
            tb.setTag(pi);
            tb.setToggleListener(createToggleListener());
            return new MyPair<MyPieItem, IMyDrawable>(pi,tb);
        }
        else if(type==1)//bicycle
        {
            Log.i("create item","color "+Color.parseColor(BLUE));
            MyToggleButton tb =new MyToggleButton(r,R.drawable.ic_bicycle,Color.parseColor(BLUE));
            tb.setBorderWidth(bw);
            MyPieItem pi =new MyPieItem(activity.getCount(),Color.parseColor(BLUE));
            tb.setTag(pi);
            tb.setToggleListener(createToggleListener());
            return new MyPair<MyPieItem, IMyDrawable>(pi,tb);
        }
        else if(type==2||type==7||type==8)//on foot, can be walking or running
        {
            Log.i("create item","color "+Color.parseColor(GREEN));
            MyToggleButton tb =new MyToggleButton(r,R.drawable.ic_onfoot,Color.parseColor(GREEN));
            tb.setBorderWidth(bw);
            MyPieItem pi =new MyPieItem(activity.getCount(),Color.parseColor(GREEN));
            tb.setTag(pi);
            tb.setToggleListener(createToggleListener());
            return new MyPair<MyPieItem, IMyDrawable>(pi,tb);
        }
        else if(type==3)//still
        {
            Log.i("create item","color "+Color.parseColor(ORANGE));
            MyToggleButton tb =new MyToggleButton(r,R.drawable.ic_still,Color.parseColor(ORANGE));
            tb.setBorderWidth(bw);
            MyPieItem pi = new MyPieItem(activity.getCount(),Color.parseColor(ORANGE));
            tb.setTag(pi);
            tb.setToggleListener(createToggleListener());
            return new MyPair<MyPieItem, IMyDrawable>(pi,tb);
        }
        else if(type==5)//tilting
        {
            Log.i("create item","color "+Color.parseColor(YELLOW));
            MyToggleButton tb =new MyToggleButton(r,R.drawable.ic_tilt,Color.parseColor(YELLOW));
            tb.setBorderWidth(bw);
            MyPieItem pi = new MyPieItem(activity.getCount(),Color.parseColor(YELLOW));
            tb.setTag(pi);
            tb.setToggleListener(createToggleListener());
            return new MyPair<MyPieItem, IMyDrawable>(pi,tb);
        }
        else
        {
            return null;
        }
    }
    private IMyOnToggleListener createToggleListener()
    {
        return new IMyOnToggleListener<MyToggleButton>() {
            @Override
            public void onToggleStateOn(MyToggleButton sender) {
                try {
                    MyPieItem pi=(MyPieItem)sender.getTag();
                    pi.setVisible(true);
                    invalidate();
                }
                catch (Exception e)
                {
                    Log.i("togglelistener","exception");
                }

            }

            @Override
            public void onToggleStateOff(MyToggleButton sender) {
                try {
                    MyPieItem pi=(MyPieItem)sender.getTag();
                    pi.setVisible(false);
                    invalidate();
                }
                catch (Exception e)
                {
                    Log.i("togglelistener","exception");
                }
            }
        };
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#696969"));

        if(dataList==null)
        {
            Paint tp= new Paint();
            tp.setStyle(Paint.Style.FILL);
            tp.setColor(Color.WHITE);
            float textSize = width < height ? width/10 : height/10;
            tp.setTextSize(textSize);
            canvas.drawText("No data yet,",0,height/2,tp);
            canvas.drawText("come back in a minute.",0,height/2+textSize,tp);
        }
        else {
            int shortestSide = width < height ? width : height;
            int pChartW = shortestSide;
            int pChartH = shortestSide;
            RectF rectChart = new RectF(0, 0, pChartW, pChartH);
            pChart.onDraw(canvas, rectChart);

            RectF rectLegend;
            if (width < height) //put legend below
            {
                legendItems.setType(MyList.MyListType.HORIZONTAL);
                float legendW = width;
                float legendH = height - pChartH;
                rectLegend = new RectF(0, pChartH, 0 + legendW, pChartH + legendH);
            } else //put legend on the right
            {
                legendItems.setType(MyList.MyListType.VERTICAL);
                float legendW = width - pChartW;
                float legendH = height;
                rectLegend = new RectF(pChartW, 0, pChartW + legendW, 0 + legendH);
            }
            legendItems.onDraw(canvas, rectLegend);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("ChartView","w"+w+" h:"+h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Log.i("tap","action:"+event.getActionMasked());
        if(event.getActionMasked()==MotionEvent.ACTION_UP) {
            legendItems.checkTap((int) event.getX(), (int) event.getY());
        }
        return true;//super.onTouchEvent(event)
    }
    public class MyPair<T,K>{
        T one;
        K two;

        public MyPair(T one, K two) {
            this.one = one;
            this.two = two;
        }

        public T getOne() {
            return one;
        }

        public K getTwo() {
            return two;
        }
    }
}
