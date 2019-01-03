package com.example.talong.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RestaurantHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lunchlist.db";
    private static final int SCHEMA_VERSION = 1;

    public RestaurantHelper(Context context)  {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }
    public RestaurantHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE restaurants(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, address TEXT, type TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String name,String address,String type){
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("address",address);
        cv.put("type",type);
        getWritableDatabase().insert("restaurants","name",cv);
    }
    public ArrayList<Restaurant> getAllData(){
        ArrayList<Restaurant> arrayList=new ArrayList<>();
        Cursor cur;
        cur = getReadableDatabase().rawQuery("SELECT _id, name, address, type  FROM restaurants ORDER BY name", null);
        while (cur.moveToNext()){
            int id=cur.getInt(0);
            String name=cur.getString(1);
            String address=cur.getString(2);
            String type=cur.getString(3);
            Restaurant restaurant=new Restaurant(id,name,address,type);
            arrayList.add(restaurant);

        }
        return arrayList;
    }

}

