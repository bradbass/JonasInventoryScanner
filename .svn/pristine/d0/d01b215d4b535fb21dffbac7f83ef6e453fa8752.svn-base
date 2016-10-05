package com.jonasconstruction.inventoryscanner.jonasinventoryscanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.lang.*;

/**
 * Created by braba on 4/15/2014..
 *
 */
public class Helper {

    Context c;
    DatabaseHandler db = new DatabaseHandler(c);
    SQLiteDatabase dbr = db.getReadableDatabase();
    SQLiteDatabase dbw = db.getWritableDatabase();

    static String _currentDateTime;
    static String _currentDate;
    static String _currentTime;

    public Helper(Context context) {
        c = context;
    }

    @SuppressLint("SimpleDateFormat")
    static String setDateTime() {
        // add DateTime to filename
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        date.setTimeZone(TimeZone.getDefault());
        _currentDateTime = date.format(currentLocalTime);

        return _currentDateTime;
    }

    @SuppressLint("SimpleDateFormat")
     static String setDate() {
        // add DateTime to filename
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        date.setTimeZone(TimeZone.getDefault());
        _currentDate = date.format(currentLocalTime);

        return _currentDate;
    }

    @SuppressLint("SimpleDateFormat")
    static String setTime() {
        // add DateTime to filename
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("HH-mm-ss");
        date.setTimeZone(TimeZone.getDefault());
        _currentTime = date.format(currentLocalTime);

        return _currentTime;
    }

    @SuppressLint("SimpleDateFormat")
    static String futureDate() {
        // take current date and increment
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        date.setTimeZone(TimeZone.getDefault());
        _currentDate = date.format(currentLocalTime);
        try {
            cal.setTime(date.parse(_currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, 30);
        _currentDate = date.format(cal.getTime());
        return _currentDate;
    }

    /**
     * @param theResponse
     * @param tag
     * @return
     */
    static String ParseJSON(String theResponse, String tag){
        final String TAG = LoginActivity.class.getSimpleName();
        try{
            String tempResp = theResponse.toUpperCase();
            String tempTag = tag.toUpperCase();
            JSONObject outer = new JSONObject(tempResp);
            //String data = (String)outer.get(tempTag);
            Boolean dataB = outer.getBoolean(tempTag);
            return dataB.toString();
        }
        catch(JSONException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    static String ParseJSONMessage(String response, String tag) {
        final String TAG = LoginActivity.class.getSimpleName();
        try{
            String tempResp = response.toUpperCase();
            String tempTag = tag.toUpperCase();
            JSONObject outer = new JSONObject(tempResp);
            return (String)outer.get(tempTag);
        }
        catch(JSONException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
}
