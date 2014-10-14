package martynov.com.libtorque.charts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

import martynov.com.libtorque.simple2d.IMyDrawable;

/**
 * Created by Oleksiy on 7/22/2014.
 */
public class MyPieChart implements IMyDrawable
{
    private List<MyPieItem> chartData;
    //private RectF rect;
    //private float numSum=0;
    public MyPieChart(List<MyPieItem> chartData)
    {
        setChartData(chartData);
        //this.rect=rect;
    }

    public void setChartData(List<MyPieItem> chartData) {
        this.chartData = chartData;
    }

    @Override
    public void onDraw(Canvas canvas,RectF rect)
    {
        float numSum=0;
        for(MyPieItem item : chartData)
        {
            if(item.isVisible()) {
                numSum += item.getNumber();
            }
        }
        float startAngle = 0;
        for(MyPieItem item : chartData)
        {
            if(item.isVisible()) {
                float sweepAngle = 360.0f * item.getNumber() / numSum;
                Paint p = new Paint();
                p.setColor(item.getColor());
                p.setStyle(Paint.Style.FILL);
                canvas.drawArc(rect, startAngle, sweepAngle, true, p);

                p.setColor(Color.parseColor("#FFFFFF"));
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(4.0f);
                canvas.drawArc(rect,startAngle,sweepAngle,true,p);
                startAngle += sweepAngle;
            }
        }
    }

}
