package martynov.com.libtorque.simple2d;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksiy on 7/17/2014.
 */
 public class MyGameView extends View
{
    Activity a;
    int scoreCpu,scoreUser;
    boolean printScore;
    float xPress, yPress;
    int width,height;
    RectF windowRect;
    Paint whitePaint;
    MyBall ball;
    MyWall paddleUser, paddleCpu;
    MyTextObj scoreText;
    List<MyGameObj> gameObjList ;
    List<IMyObjCollidable> collidableObjList ;
    private boolean doWork =true;
    Handler handler = new Handler();
    final long miliPerFrame =1000/59;
    private Runnable work = new Runnable() {
        @Override
        public void run() {
            if(doWork) {
                postInvalidate();
                onUpdate((int) miliPerFrame);
                handler.postDelayed(work,miliPerFrame);
            }
        }
    };

    public MyGameView(Activity activity, Point size)
    {
        super(activity);
        a=activity;
        width = size.x;
        height = size.y;
        windowRect=new RectF(0.0f,0.0f,width,height);
        scoreCpu=0;
        scoreUser=0;
         whitePaint =new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);


        setLevel(scoreCpu, scoreUser);
    }

    private void setLevel(final int scoreCpu, final int scoreUser)
    {

        doWork=false;


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //wait for a second so user can read the score
                doWork=true;
                handler.postDelayed(work,miliPerFrame);
            }
        },1000);

        gameObjList = new ArrayList();
        collidableObjList = new ArrayList();


        float smaller =width>height?height:width;
        float ballLeft = smaller/2.0f;
        float ballTop = smaller/2.0f;//not height because we want a rectangle
        float ballRight = ballLeft*1.1f;
        float ballBottom = ballTop*1.1f;

        ball = new MyBall(new RectF((int)ballLeft,(int)ballTop,(int)ballRight,(int)ballBottom),whitePaint);

        float r =(float)(Math.random()*100);
        //Log.i("test","r:"+r);
        if(Math.random()>0.5)
        {
            ball.setVelocity(new MyVector<Float>(180.0f, 0.0f+r, 0.0f));
        }
        else
        {
            ball.setVelocity(new MyVector<Float>(180.0f, 0.0f-r, 0.0f));
        }

        gameObjList.add(ball);
        collidableObjList.add(ball);


        float wallWidth =width;
        float wallHeight=(height*0.1f);

        float verticalOffset = wallHeight*0.5f;

        MyWall wallTop = new MyWall(new RectF(0,0-verticalOffset,wallWidth,0-verticalOffset+wallHeight),whitePaint);
        gameObjList.add(wallTop);
        collidableObjList.add(wallTop);


        MyWall wallBottom = new MyWall(new RectF(0,height-verticalOffset,wallWidth,height-verticalOffset+wallHeight),whitePaint);
        gameObjList.add(wallBottom);
        collidableObjList.add(wallBottom);

        xPress=width/2;
        yPress=height/2;

        float paddleWidth = width*0.1f;
        float paddleHeight =height*0.25f;


        paddleUser =new MyWall(new RectF(width-paddleWidth,verticalOffset+height/2-paddleHeight/2,width,verticalOffset+height/2+paddleHeight/2),whitePaint);
        gameObjList.add(paddleUser);
        collidableObjList.add(paddleUser);


        paddleCpu =  new MyWall(new RectF(0,verticalOffset+height/2-paddleHeight/2,0+paddleWidth,verticalOffset+height/2+paddleHeight/2),whitePaint);
        gameObjList.add(paddleCpu);
        collidableObjList.add(paddleCpu);

        scoreText = new MyTextObj(new RectF(width/2-50,height/2-50,width/2+50,height/2+50),whitePaint,scoreCpu+":"+scoreUser,60,1000);
        gameObjList.add(scoreText);
    }
    private void updatePaddles()
    {
        paddleUser.setPosition(new MyVector<Float>(paddleUser.getPosition().getX(),yPress-paddleUser.getHeight()/2,0.0f));
        //paddleUser.setPosition(new MyVector<Float>(paddleUser.getPosition().getX(),ball.getPosition().getY()-paddleUser.getHeight()/2,0.0f)); // testing only
        float maxInc=60*(scoreUser+1)/(scoreCpu+1);
        float minInc=40;
        autoControl(ball,paddleCpu,scoreCpu,scoreUser);//testing only
        //autoControl(ball,paddleUser,scoreUser,scoreCpu);
    }
    private void autoControl(MyBall b, MyWall paddle, float scoreThis, float scoreOther)
    {
        float incr=200*(scoreOther+1)/(scoreThis+1);

        //float minInc=40;
        float paddleCenter=paddle.getPosition().getY()+paddle.getHeight()/2;
        float ballCenter=b.getPosition().getY()+b.getHeight()/2;
        float dm =Math.abs((ballCenter-paddleCenter)/height*8.0f);
        if(paddleCenter<ballCenter)//centre of paddle is lower than center of ball
        {
            paddle.setVelocity(new MyVector<Float>(0.0f,incr*dm,0.0f));
        }
        else if(paddleCenter>ballCenter)
        {
            paddle.setVelocity(new MyVector<Float>(0.0f,-incr*dm,0.0f));
        }



    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        doWork=true;
        handler.postDelayed(work,miliPerFrame);
        Log.i("GameView","attached");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        doWork=false;
        handler.removeCallbacksAndMessages(null);
        Log.i("GameView","detached");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        for(MyGameObj obj : gameObjList)
        {
            obj.onDraw(canvas);
        }

    }

    private void onUpdate(int mili)
    {
        updatePaddles();
        for(MyGameObj obj : gameObjList)
        {
            obj.onUpdate(mili);
        }
        for(IMyObjCollidable colObj : collidableObjList)
        {
            for(IMyObjCollidable colObjOther : collidableObjList)
            {
                if(colObj!=colObjOther)
                {
                    colObj.checkColission(colObjOther);
                }
            }
        }
        RectF ballRect=ball.getRectangle();
        if(!RectF.intersects(ballRect,windowRect))
        {

            if(ballRect.right<windowRect.left)//out from left side
            {
                setLevel(scoreCpu,++scoreUser);
            }
            else if(ballRect.left>windowRect.right)//out from right
            {
                setLevel(++scoreCpu,scoreUser);
            }
            /*
            else// bounced from top or bottom,
            {
                setLevel(scoreCpu,scoreUser);//reset
            }*/

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xPress=event.getX();
        yPress=event.getY();
        return true;
    }

}
