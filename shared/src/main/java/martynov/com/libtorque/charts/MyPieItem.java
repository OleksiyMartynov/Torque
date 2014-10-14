package martynov.com.libtorque.charts;

/**
 * Created by Oleksiy on 7/22/2014.
 */
public class MyPieItem
{
    static private int id=0;
    private float number;
    private int color;
    private boolean visible=true;
    private int thisId;

    public MyPieItem(float number, int color) {
        this.number = number;
        this.color = color;
        thisId=id++;
    }

    public int getThisId() {
        return thisId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }
}
