package martynov.com.libtorque.simple2d;

import android.content.res.Resources;
import android.graphics.Color;

/**
 * Created by Oleksiy on 7/23/2014.
 */
public class MyToggleButton extends MyButton {
    IMyOnToggleListener toggleListener;
    private boolean toggleState =true;
    int oldColor = Color.parseColor("#BFBFBF");
    public MyToggleButton(Resources resources, int bmpId, int color) {
        super(resources, bmpId, color);
    }

    public void setToggleListener(IMyOnToggleListener toggleListener) {
        this.toggleListener = toggleListener;
    }

    @Override
    public void checkTap(int x, int y) {
        if(getRect()!=null)
        {
            if(getRect().contains(x,y))
            {
                toggleState=!toggleState;
                int tempColor = getColor();
                setColor(oldColor);
                oldColor=tempColor;
                if(toggleListener!=null)
                {
                    if(toggleState)
                    {
                        toggleListener.onToggleStateOn(this);
                    }
                    else
                    {
                        toggleListener.onToggleStateOff(this);
                    }
                }
            }
        }
        super.checkTap(x, y);
    }

}
