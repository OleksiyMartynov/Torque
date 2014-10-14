package martynov.com.libtorque.simple2d;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksiy on 7/22/2014.
 */
public class MyList implements  IMyDrawable, IMyAcceptsTaps
{
    private RectF rect;
    private List<IMyDrawable> items ;
    private MyListType type;
    private boolean squareItems=false;
    public MyList(MyListType type) {
        this.type = type;
    }

    public void setType(MyListType type) {
        this.type = type;
    }

    public void setItems(List<IMyDrawable> items) {
        this.items = items;
    }
    public void addItem(IMyDrawable item)
    {
        if(items==null)
        {
            items = new ArrayList<IMyDrawable>();
        }
        items.add(item);
    }

    public void setSquareItems(boolean squareItems) {
        this.squareItems = squareItems;
    }

    @Override
    public void onDraw(Canvas canvas, RectF rect) {
        this.rect=rect;
        if(items!=null&&items.size()>0) {
            float allowedWidth = rect.right - rect.left;
            float allowedHeight = rect.bottom - rect.top;

            float itemWidth = type == MyListType.HORIZONTAL ? allowedWidth / items.size() : allowedWidth;
            float itemHeight = type == MyListType.VERTICAL ? allowedHeight / items.size() : allowedHeight;
            if(squareItems)
            {
                itemWidth=itemWidth<itemHeight?itemWidth:itemHeight;
                itemHeight=itemWidth;
            }
            float offset = type == MyListType.HORIZONTAL ? allowedWidth / items.size() : allowedHeight / items.size();

            for (int i = 0; i < items.size(); i++) {
                float x = type == MyListType.HORIZONTAL ? i * offset  : rect.left;
                float y = type == MyListType.VERTICAL ? i * offset : rect.top;

                IMyDrawable drawable = items.get(i);
                drawable.onDraw(canvas, new RectF(x, y, x + itemWidth, y + itemHeight));
            }
        }
    }

    @Override
    public void checkTap(int x, int y) {
        if(rect!=null)
        {
            if(items!=null)
            {
                for(IMyDrawable d:items)
                {
                    if(d instanceof IMyAcceptsTaps)
                    {
                        ((IMyAcceptsTaps)d).checkTap(x, y);
                    }
                }
            }
        }
    }

    public enum MyListType{HORIZONTAL,VERTICAL};
}
