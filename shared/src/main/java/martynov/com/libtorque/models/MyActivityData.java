package martynov.com.libtorque.models;

/**
 * Created by Oleksiy on 7/20/2014.
 */
public class MyActivityData
{
    private int id=-1;
    private int type;
    private int count;
    private int fkId=-1;
    private MyDate date;

    public MyActivityData(int id, int fkId, int type, int count, MyDate date) {
        this.id = id;
        this.type = type;
        this.count = count;
        this.fkId = fkId;
        this.date = date;
    }

    public MyActivityData(int id, int fkId, int type, int count) {
        this.fkId = fkId;
        this.id = id;
        this.type = type;
        this.count = count;
    }

    public MyActivityData(int type, int count) {
        this.type = type;
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public int getFkId() {
        return fkId;
    }

    public MyDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "MyActivityData{" +
                "id=" + id +
                ", type=" + type +
                ", count=" + count +
                ", fkId=" + fkId +
                ", date='" + date.toString() + '\'' +
                '}';
    }
}
