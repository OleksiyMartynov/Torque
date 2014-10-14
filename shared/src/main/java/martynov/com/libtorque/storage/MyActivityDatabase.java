package martynov.com.libtorque.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import martynov.com.libtorque.models.MyActivityData;
import martynov.com.libtorque.models.MyDate;
import martynov.com.libtorque.models.MyDayData;

/**
 * Created by Oleksiy on 7/20/2014.
 */
public class MyActivityDatabase extends  MyDatabase {
    public static final String TAG ="MyActivityDatabase";
    public static final String DB_NAME = "Activity_Database";
    public static final int DB_VERSION = 5;
    public enum GROUP_BY{MONTH,WEEK,DAY};
    public enum TABLES{T_MAIN,T_DETAIL};
    public enum T_MAIN{C_MAIN_ID,C_DAY_OF_WEEK,C_DAY_OF_MONTH,C_MONTH,C_YEAR};
    public enum T_DETAIL{C_DETAIL_ID,C_FK_MAIN_ID,C_TYPE,C_COUNT};
    private static final String T_MAIN_CREATE_QUERY="CREATE TABLE IF NOT EXISTS "+TABLES.T_MAIN+" ( "+T_MAIN.C_MAIN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+T_MAIN.C_DAY_OF_WEEK+" TEXT ,"+T_MAIN.C_DAY_OF_MONTH+" TEXT ,"+T_MAIN.C_MONTH+" TEXT ,"+T_MAIN.C_YEAR+" TEXT "+");";
    private static final String T_MAIN_UPGRADE_QUERY="DROP TABLE IF EXISTS "+TABLES.T_MAIN+";";
    private static final String T_DETAIL_CREATE_QUERY="CREATE TABLE IF NOT EXISTS "+TABLES.T_DETAIL +" ( "+T_DETAIL.C_DETAIL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+T_DETAIL.C_FK_MAIN_ID+" INTEGER, "+ T_DETAIL.C_TYPE+" INTEGER, "+T_DETAIL.C_COUNT+" INTEGER );";
    private static final String T_DETAIL_UPGRADE_QUERY="DROP TABLE IF EXISTS "+TABLES.T_DETAIL+";";
    public MyActivityDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION, new String[]{T_MAIN_CREATE_QUERY,T_DETAIL_CREATE_QUERY}, new String[]{T_MAIN_UPGRADE_QUERY,T_MAIN_CREATE_QUERY,T_DETAIL_UPGRADE_QUERY,T_DETAIL_CREATE_QUERY});
    }
    public void saveActivity(DetectedActivity activity)
    {
        saveActivity(activity.getType(), MyDate.today());
    }
    public void saveActivity(int activityType,MyDate date)
    {
        int dayId = dayExists(date);
        if(dayId<0)//new day
        {
            saveDay(date);
            dayId=getMaxInt(TABLES.T_MAIN.toString(),T_MAIN.C_MAIN_ID.toString());
        }
        int[] arrData = activityCount(activityType,dayId);
        int counter=arrData[0];
        Log.i(TAG,"addData:"+arrData[0]+","+arrData[1]);
        if(counter<=0) //create new activity type
        {
            counter =1;
            Log.i(TAG,"new activity type of "+activityType);
            this.executeQuery("INSERT INTO "+TABLES.T_DETAIL+" ( "+T_DETAIL.C_FK_MAIN_ID+", "+T_DETAIL.C_TYPE+", "+T_DETAIL.C_COUNT+" )"+
                    "VALUES ("+ dayId+", "+activityType+", "+counter+" )");
        }
        else  //update existing activity type
        {
            ++counter;
            Log.i(TAG,"updating activity type of "+activityType);
            this.executeQuery("UPDATE "+TABLES.T_DETAIL +" SET "+T_DETAIL.C_COUNT+" = "+counter +" WHERE "+T_DETAIL.C_DETAIL_ID+" = "+arrData[1]+";");
        }


    }
    private void saveDay(MyDate date)
    {
        this.executeQuery("INSERT INTO "+ TABLES.T_MAIN + " ( "+T_MAIN.C_DAY_OF_WEEK+" , "+T_MAIN.C_DAY_OF_MONTH+" , "+T_MAIN.C_MONTH+" , "+T_MAIN.C_YEAR+ " ) "+ " VALUES ( '"+date.getDayOfWeek()+"',"+"'"+date.getDayOfMonth()+"',"+"'"+date.getMonth()+"',"+"'"+date.getYear()+"');");
    }

    public List<MyDayData> getGroupedActivityData(String whereStr, GROUP_BY groupType)
    {
        int DAYS_IN_WEEK =7;
        int MONTH_IN_YEAR =12;
        List<List<String>> rows = new ArrayList<List<String>>();
        String query = "SELECT "+T_DETAIL.C_TYPE+","+" SUM("+T_DETAIL.C_COUNT+")"+","+ T_MAIN.C_DAY_OF_WEEK+","+ T_MAIN.C_DAY_OF_MONTH+","+ T_MAIN.C_MONTH+","+ T_MAIN.C_YEAR+" FROM "+TABLES.T_MAIN+" JOIN "+ TABLES.T_DETAIL+" ON "+ TABLES.T_MAIN+"."+T_MAIN.C_MAIN_ID+"="+TABLES.T_DETAIL+"."+T_DETAIL.C_FK_MAIN_ID+" GROUP BY "+T_DETAIL.C_TYPE+","+T_DETAIL.C_COUNT+" "+whereStr+";";
        if(groupType == GROUP_BY.DAY) //where string should have day number , month name and year
        {

            rows = this.executeQuery(query);
        }
        else if(groupType == GROUP_BY.WEEK) //where string should have month name and year
        {

            rows = this.executeQuery(query);
        }
        else //month . where string should be empty
        {

            rows = this.executeQuery(query);
        }

        List<MyDayData> result = new ArrayList<MyDayData>();
        if(rows!=null&&rows.size()>0)//at one row of data is returned by query
        {
            try{
                for(List<String> row : rows)
                {
                    result.add(new MyDayData(Integer.valueOf(row.get(0)),new MyDate(row.get(1),row.get(2),row.get(3),row.get(4)),context));
                }
            }
            catch (Exception e)
            {
                Log.i(TAG,"exception in getWeekDays");
            }
        }
        else
        {
            Log.i(TAG,"getWeekDays "+(rows==null?"query returned null list":"no rows returned"));
        }
        return result;
    }
    public List<MyDayData> getWeekDays(int weekNumber)//if week is number 0 then we return last 7 days, if week number 1 then we return 7 days from 1 week ago. weekNumber is an offset
    {
        int DAYS_IN_WEEK =7;
        List<List<String>>rows=this.executeQuery("SELECT * FROM "+ TABLES.T_MAIN +" ORDER BY "+T_MAIN.C_MAIN_ID+" DESC "+ " LIMIT "+DAYS_IN_WEEK+ " OFFSET "+weekNumber+";");
        List<MyDayData> result = new ArrayList<MyDayData>();
        if(rows!=null&&rows.size()>0)//at one row of data is returned by query
        {
            try{
                for(List<String> row : rows)
                {
                    result.add(new MyDayData(Integer.valueOf(row.get(0)),new MyDate(row.get(1),row.get(2),row.get(3),row.get(4)),context));
                }
            }
            catch (Exception e)
            {
                Log.i(TAG,"exception in getWeekDays");
            }
        }
        else
        {
            Log.i(TAG,"getWeekDays "+(rows==null?"query returned null list":"no rows returned"));
        }
        return result;

    }
    public MyDayData getDay(int dayId)//if week is number 0 then we return last 7 days, if week number 1 then we return 7 days from 1 week ago. weekNumber is an offset
    {
        List<List<String>>rows=this.executeQuery("SELECT * FROM "+ TABLES.T_MAIN +" WHERE "+T_MAIN.C_MAIN_ID+ " = "+dayId+";");

        if(rows!=null&&rows.size()>0)//at one row of data is returned by query
        {
            try{
                for(List<String> row : rows)
                {
                   return new MyDayData(Integer.valueOf(row.get(0)),new MyDate(row.get(1),row.get(2),row.get(3),row.get(4)),context);
                }
            }
            catch (Exception e)
            {
                Log.i(TAG,"exception in getDay");
            }
        }
        else
        {
            Log.i(TAG,"getDay "+(rows==null?"query returned null list":"no rows returned"));
        }
        return null;

    }
    public List<MyActivityData> getActivities(int fkId)
    {
        List<List<String>>rows=this.executeQuery("SELECT * FROM "+TABLES.T_DETAIL + " WHERE " + T_DETAIL.C_FK_MAIN_ID + " = "+fkId+" ;");
        List<MyActivityData> result = new ArrayList<MyActivityData>();
        if(rows!=null&&rows.size()>0)//at one row of data is returned by query
        {
            try{
                for(List<String> row : rows)
                {
                    result.add(new MyActivityData(Integer.valueOf(row.get(0)),Integer.valueOf(row.get(1)),Integer.valueOf(row.get(2)),Integer.valueOf(row.get(3))));
                }
            }
            catch (Exception e)
            {
                Log.i(TAG,"exception in getActivities "+e.getMessage());
            }
        }
        else
        {
            Log.i(TAG,"getActivities "+(rows==null?"query returned null list":"no rows returned"));
        }
        return result;
    }
    private int getMaxInt(String tableName,String columnName)
    {
        List<List<String>>rows=this.executeQuery("SELECT MAX("+columnName+") FROM "+tableName+" ;");
        if(rows!=null&&rows.size()>0) {
            try {
                return Integer.valueOf(rows.get(0).get(0));
            } catch (Exception e) {
                Log.w(TAG, "check getMaxInt Function");
                return -1;
            }
        }
        else
        {
            Log.w(TAG, "getMaxInt query, no items in table");
        }
        return -1;
    }
    public int dayExists(MyDate date)
    {
        List<List<String>>rows=this.executeQuery("SELECT "+T_MAIN.C_MAIN_ID+
                " FROM "+TABLES.T_MAIN+
                " WHERE "+T_MAIN.C_DAY_OF_WEEK+" = '"+date.getDayOfWeek()+"'  " +
                "AND "+T_MAIN.C_DAY_OF_MONTH+" = '"+date.getDayOfMonth()+"'  " +
                "AND "+T_MAIN.C_MONTH+" = '"+date.getMonth()+"'  " +
                "AND "+T_MAIN.C_YEAR+" = '"+date.getYear()+"'  " +";");
        if(rows!=null&&rows.size()>0) {
            try {
                if(rows.get(0)!=null&&rows.get(0).size()>0) {
                    return Integer.valueOf(rows.get(0).get(0));
                }
            } catch (Exception e) {
                Log.w(TAG, "check dayExists Function");
                return -1;
            }
        }
        else
        {
            Log.w(TAG, "dayExists query, no items in table");
        }
        return -1;
    }
    private int[] activityCount(int activityType, int fkID)//returns the count column , wich represents a unit of time, probably minutes
    {
        List<List<String>>rows=this.executeQuery("SELECT "+T_DETAIL.C_COUNT+" , "+T_DETAIL.C_DETAIL_ID+" FROM "+TABLES.T_DETAIL+" WHERE "+T_DETAIL.C_FK_MAIN_ID+" = "+fkID+" AND "+T_DETAIL.C_TYPE+" = "+activityType+";");
        if(rows!=null&&rows.size()>0) {
            try {
                if(rows.get(0)!=null&&rows.get(0).size()>0) {
                    return new int[]{Integer.valueOf(rows.get(0).get(0)),Integer.valueOf(rows.get(0).get(1))};//count, id
                }
            } catch (Exception e) {
                Log.w(TAG, "activityCount dayExists Function");
                return new int[]{-1,-1};
            }
        }
        else
        {
            Log.w(TAG, "activityCount query, no items in table");
        }
        return new int[]{-1,-1};
    }
}
