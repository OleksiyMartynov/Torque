package martynov.com.libtorque.charts;

/**
 * Created by Oleksiy on 7/24/2014.
 */
public class MyBarItem
{
    private float number;
    private int color;

    public MyBarItem(float number, int color) {
        this.number = number;
        this.color = color;
    }

    public float getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "MyBarItem{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }
}