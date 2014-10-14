package martynov.com.libtorque.charts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyTitledBar extends MyBar {
    private String title;
    public MyTitledBar(ArrayList<MyBarItem> data, String title) {
        super(data);
        this.title=title;
    }

    @Override
    public void onDraw(Canvas canvas, RectF rect) {
        //assume that bar is horizontal
        float widthBar =rect.width()*0.80f;
        float widthTitle=rect.width()-widthBar;
        RectF rectBar = new RectF(rect.left,rect.top,rect.left+widthBar,rect.bottom);
        super.onDraw(canvas, rectBar);

        RectF rectTitle = new RectF(rectBar.right,rect.top,rectBar.right+widthTitle,rect.bottom);
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(35f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title,rectTitle.centerX(),rectTitle.centerY(),textPaint);
    }
}
