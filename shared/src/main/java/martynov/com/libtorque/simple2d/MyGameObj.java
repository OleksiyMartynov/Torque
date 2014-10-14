package martynov.com.libtorque.simple2d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Oleksiy on 7/17/2014.
 */
public class MyGameObj
{
    private RectF dimensions;
    private MyVector<Float> position;
    private MyVector<Float> velocity = new MyVector<Float>(0.0f,0.0f,0.0f);
    private Paint paint ;

    public MyGameObj(RectF dimensions, Paint paint ) {
        this.dimensions = dimensions;
        this.position = new MyVector<Float>(dimensions.left,dimensions.top,0.0f);
        this.paint=paint;
    }

    public void setPosition(MyVector<Float> position) {
        this.position = position;
    }

    public void setVelocity(MyVector<Float> velocity) {
        this.velocity = velocity;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public MyVector<Float> getPosition() {
        return position;
    }

    public RectF getDimensions() {
        return dimensions;
    }

    public MyVector<Float> getVelocity() {
        return velocity;
    }

    public float getWidth()
    {
        return dimensions.right-dimensions.left;
    }

    public float getHeight()
    {
        return dimensions.bottom-dimensions.top;
    }

    public void onUpdate(int miliDelta)
    {
        float d = (float)miliDelta/1000.0f;
        position=new MyVector<Float>(position.getX()+velocity.getX()*d,position.getY()+velocity.getY()*d,position.getZ()+velocity.getZ()*d);
        float width = getWidth();
        float height = getHeight();
        dimensions=new RectF(position.getX().intValue(),position.getY().intValue(),position.getX().intValue()+width,position.getY().intValue()+height);
    }
    public void onDraw(Canvas canvas)
    {
        canvas.drawRect(getDimensions(),getPaint());
    }
}
