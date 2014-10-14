package martynov.com.libtorque.simple2d;

/**
 * Created by Oleksiy on 7/17/2014.
 */
public class MyVector<T>
{
    private T x,y,z;

    public MyVector(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public T getZ() {
        return z;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {
        this.y = y;
    }

    public void setZ(T z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "MyVector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
