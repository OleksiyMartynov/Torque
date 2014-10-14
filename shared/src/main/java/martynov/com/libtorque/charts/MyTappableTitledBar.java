package martynov.com.libtorque.charts;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import martynov.com.libtorque.simple2d.IMyAcceptsTaps;
import martynov.com.libtorque.simple2d.IMyOnTapListener;
import martynov.com.libtorque.simple2d.IMyTappable;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyTappableTitledBar extends MyTitledBar implements IMyTappable, IMyAcceptsTaps {
    private RectF rect;
    private IMyOnTapListener listener;
    public MyTappableTitledBar(ArrayList<MyBarItem> data, String title) {
        super(data, title);
    }

    @Override
    public void checkTap(int x, int y) {
        if(rect!=null&&rect.contains(x,y))
        {
            if(listener!=null)
            {
                listener.onTap(this);
            }
        }
    }

    @Override
    public void setOnTapListener(IMyOnTapListener listener) {
        this.listener=listener;
    }

    @Override
    public void onDraw(Canvas canvas, RectF rect) {
        super.onDraw(canvas, rect);
        this.rect=rect;
    }
}
