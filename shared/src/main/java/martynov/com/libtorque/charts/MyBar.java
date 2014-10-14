package martynov.com.libtorque.charts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;

import martynov.com.libtorque.charts.MyBarItem;
import martynov.com.libtorque.simple2d.IMyDrawable;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyBar implements IMyDrawable {
    private ArrayList<MyBarItem> data;
    private String title;
    private boolean showPercent = true;
    public MyBar(ArrayList<MyBarItem> data) {
        this.data = data;
    }

    public void setShowPercent(boolean showPercent) {
        this.showPercent = showPercent;
    }

    public void setData(ArrayList<MyBarItem> data) {
        this.data = data;
    }

    @Override
    public void onDraw(Canvas canvas, RectF rect) {
        float numSum = 0;
        for(MyBarItem item : data)
        {
            numSum+=item.getNumber();
        }
        //assuming horizontal bar
        float xOffset =0;
        for(int i =0; i<data.size(); i++)
        {
            Log.i("bar item", "drawing #"+i);
            MyBarItem bi = data.get(i);
            float multX =bi.getNumber()/numSum;
            float width = rect.width()*multX;
            float height =rect.height();

            float yOffset =rect.top;
            RectF r = new RectF(xOffset,yOffset,xOffset+width,yOffset+height);
            xOffset+=rect.left+width;//add up the offset
            Log.i("bar rect",r.toString());
            Paint p = new Paint();
            p.setColor(bi.getColor());
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(r,p);
            if(showPercent)
            {
                Paint textPaint = new Paint();
                textPaint.setColor(Color.WHITE);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setTextSize(35f);
                textPaint.setTextAlign(Paint.Align.CENTER);
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(0);
                nf.setGroupingUsed(false);
                String label = nf.format(100f*multX)+"%";

                if(textPaint.measureText(label)<r.width())//text fits without going outside of the rect
                {
                    canvas.drawText(label,r.centerX(),r.centerY(),textPaint);
                }
            }
            //border
            p.setStyle(Paint.Style.STROKE);//border prep
            p.setColor(Color.WHITE);
            p.setStrokeWidth(4.0f);
            canvas.drawRect(r,p);//draw border
        }
    }

}
