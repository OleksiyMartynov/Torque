package martynov.com.libtorque.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import martynov.com.libtorque.models.MyGroupedData;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyDataRowItemView extends View {
    private MyGroupedData data;
    private int width,height;
    public MyDataRowItemView(Context context) {
        super(context);
    }

    public void setData(MyGroupedData data) {
        this.data = data;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setTextSize(30);
        p.setColor(Color.BLACK);
        canvas.drawText("blah",0,0,p);
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
        return super.onTouchEvent(event);
    }
}
