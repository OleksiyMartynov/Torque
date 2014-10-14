package martynov.com.libtorque.simple2d;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Oleksiy on 7/23/2014.
 */
public class MyButton<T> extends MyRectangle implements IMyTappable, IMyAcceptsTaps {

    IMyOnTapListener<MyRectangle> listener;
    RectF rect;
    T tag;
    public MyButton(Resources resources, int bmpId, int color) {
        super(resources, bmpId, color);
    }

    public RectF getRect() {
        return rect;
    }

    public T getTag() {
        return tag;
    }

    public void setTag(T tag) {
        this.tag = tag;
    }

    @Override
    public void setOnTapListener(IMyOnTapListener listener) {
        this.listener=listener;
    }

    @Override
    public void checkTap(int x, int y) {
        if(rect!=null)
        {
            if(listener!=null)
            {
                if(rect.contains(x,y))
                {
                    listener.onTap(this);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas, RectF rect) {
        super.onDraw(canvas, rect);
        this.rect=rect;
    }
}
