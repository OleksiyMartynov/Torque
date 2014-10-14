package martynov.com.libtorque.simple2d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;

/**
 * Created by Oleksiy on 7/17/2014.
 */
public class MyTextObj extends MyGameObj {
    private String text;
    boolean visible=true;
    public MyTextObj(RectF dimensions, Paint paint,String text, float charSize) {
        super(dimensions, paint);
        paint.setTextSize(charSize);
        this.text=text;
    }
    public MyTextObj(RectF dimensions, Paint paint,String text, float charSize, long dissapearAfterMili) {
        super(dimensions, paint);
        paint.setTextSize(charSize);
        this.text=text;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                visible=false;
            }
        },dissapearAfterMili);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        if(visible) {
            canvas.drawText(text, getPosition().getX(), getPosition().getX(), getPaint());
        }
    }
}
