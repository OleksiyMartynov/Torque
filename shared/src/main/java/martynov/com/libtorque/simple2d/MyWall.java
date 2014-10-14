package martynov.com.libtorque.simple2d;


import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Oleksiy on 7/17/2014.
 */
public class MyWall extends MyGameObj implements IMyObjCollidable
{


    public MyWall(RectF dimensions, Paint paint) {
        super(dimensions, paint);
    }

    @Override
    public RectF getRectangle() {
        return getDimensions();
    }

    @Override
    public void checkColission(IMyObjCollidable collidable) {
        //do nothing becausse its a wall
    }


}
