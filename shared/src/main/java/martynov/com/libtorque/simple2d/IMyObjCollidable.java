package martynov.com.libtorque.simple2d;

import android.graphics.RectF;

/**
 * Created by Oleksiy on 7/17/2014.
 */
public interface IMyObjCollidable
{
    MyVector<Float> getVelocity();
    RectF getRectangle();
    void checkColission(IMyObjCollidable collidable);
}
