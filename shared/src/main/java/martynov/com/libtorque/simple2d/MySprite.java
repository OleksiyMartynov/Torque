package martynov.com.libtorque.simple2d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Oleksiy on 7/22/2014.
 */
public class MySprite implements IMyDrawable {
    private int rId;
    private Resources resources;
    public MySprite(int bitmapId, Resources resources)
    {
        this.rId=bitmapId;
        this.resources=resources;
    }
    @Override
    public void onDraw(Canvas canvas, RectF rect) {
        Bitmap bmp = BitmapFactory.decodeResource(resources,rId);
        canvas.drawBitmap(bmp,new Rect(0,0,bmp.getWidth(),bmp.getHeight()),rect,null);
        bmp.recycle();
    }
}
