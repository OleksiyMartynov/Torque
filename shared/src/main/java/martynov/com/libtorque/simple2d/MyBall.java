package martynov.com.libtorque.simple2d;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Oleksiy on 7/17/2014.
 */
public class MyBall extends MyGameObj implements IMyObjCollidable
{
    MyVector<Float> prevPos;
    private static final float maxVelocity = 600;
    private static final float minVelocity = 200;
    public MyBall(RectF dimensions, Paint paint) {
        super(dimensions, paint);
        prevPos= new MyVector<Float>(getPosition().getX(),getPosition().getY(),getPosition().getZ());
    }

    @Override
    public RectF getRectangle() {
        return getDimensions();
    }

    @Override
    public void checkColission(IMyObjCollidable collidable)
    {
        RectF thisRect = getRectangle();
        RectF otherRect=collidable.getRectangle();
        if(RectF.intersects(thisRect,otherRect))
        {

            if(otherRect.contains(thisRect.left,thisRect.top,thisRect.left+2,thisRect.bottom))//check left
            {
                getVelocity().setX(-getVelocity().getX() * 1.15f);
                float r =(float)(Math.random()*0.25*maxVelocity);
                //Log.i("test","r:"+r);
                if(Math.random()>0.5)
                {
                    getVelocity().setY(getVelocity().getY()+r);
                }
                else
                {
                    getVelocity().setY(getVelocity().getY()-r);
                }
                //setPosition(new MyVector<Float>(otherRect.right,getPosition().getY(),0.0f));
                setPosition(prevPos);

            }
            else if(otherRect.contains(thisRect.right-2,thisRect.top,thisRect.right,thisRect.bottom))//check right
            {
                getVelocity().setX(-getVelocity().getX() * 1.15f);
                float r =(float)(Math.random()*0.25*maxVelocity);
                //Log.i("test","r:"+r);
                if(Math.random()>0.5)
                {
                    getVelocity().setY(getVelocity().getY()+r);
                }
                else
                {
                    getVelocity().setY(getVelocity().getY()-r);
                }
                //setPosition(new MyVector<Float>(otherRect.left-getWidth(),getPosition().getY(),0.0f));
                setPosition(prevPos);

            }
             if(otherRect.contains(thisRect.left,thisRect.top,thisRect.right,thisRect.top+2))//check top
            {
                getVelocity().setY(-getVelocity().getY()*1.05f);

                setPosition(new MyVector<Float>(getPosition().getX(),otherRect.bottom,0.0f));

            }
            else if(otherRect.contains(thisRect.left,thisRect.bottom-2,thisRect.right,thisRect.bottom))//check bottom
            {
                getVelocity().setY(-getVelocity().getY()*1.05f);

                setPosition(new MyVector<Float>(getPosition().getX(),otherRect.top-getHeight(),0.0f));

            }

        }
    }

    @Override
    public void onUpdate(int miliDelta) {
        /*
        if(getVelocity().getX()>maxVelocity)
        {
            getVelocity().setX(maxVelocity);
        }*/
        if(getVelocity().getY()>maxVelocity)
        {
            getVelocity().setY(maxVelocity);
        }
        /*
        if(getVelocity().getX()<minVelocity)
        {
            getVelocity().setX(minVelocity);
        }
        if(getVelocity().getY()<minVelocity)
        {
            getVelocity().setY(minVelocity);
        }*/
        prevPos= new MyVector<Float>(getPosition().getX(),getPosition().getY(),getPosition().getZ());
        super.onUpdate(miliDelta);
    }
}
