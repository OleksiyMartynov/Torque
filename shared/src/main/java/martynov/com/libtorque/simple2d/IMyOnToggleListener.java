package martynov.com.libtorque.simple2d;

/**
 * Created by Oleksiy on 7/23/2014.
 */
public interface IMyOnToggleListener<T> {
    void onToggleStateOn(T sender);
    void onToggleStateOff(T sender);
}
