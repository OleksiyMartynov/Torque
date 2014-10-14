package martynov.com.libtorque.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleksiy on 8/1/2014.
 */
public class MyDate
{
    private String dayOfWeek;
    private String dayOfMonth;
    private String month;
    private String year;

    public MyDate(String dayOfWeek, String dayOfMonth, String month, String year) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public static MyDate today()
    {
        Date now=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        String dw=formatter.format(now);
        formatter=new SimpleDateFormat("d");
        String dm = formatter.format(now);
        formatter = new SimpleDateFormat("MMMM");
        String m=formatter.format(now);
        formatter = new SimpleDateFormat("yyyy");
        String y = formatter.format(now);
        return new MyDate(dw,dm,m,y);
    }
    public String toShortString()
    {
        return dayOfWeek+" "+dayOfMonth+" "+month+" "+year;
    }
    @Override
    public String toString() {
        return "MyDate{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", dayOfMonth='" + dayOfMonth + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
