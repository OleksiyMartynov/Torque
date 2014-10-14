package martynov.com.libtorque.simple2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by Oleksiy on 7/22/2014.
 */
public class MyRectangle implements IMyDrawable  {
    private int color;
    private MySprite icon;
    private Paint p;
    private float borderWidth=0;
    public MyRectangle(Resources resources, int bmpId, int color) {
        icon = new MySprite(bmpId,resources);
        this.color=color;
        p= new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(color);
    }
    public void setColor(int color) {
        p.setColor(color);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    @Override
    public void onDraw(Canvas canvas, RectF rect) {

        Log.i("rect", "c:" + color + "w" + (rect.right - rect.left));
        canvas.drawRect(rect, p);
        if(icon!=null)
        {
            icon.onDraw(canvas,rect);
        }
        if(borderWidth>0)
        {
            Paint borderPaint = new Paint();
            borderPaint.setColor(Color.WHITE);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderWidth);
            canvas.drawRect(rect, borderPaint);
        }
    }


}
