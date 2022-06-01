package com.example.myapplication.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// can work on it with any project
public class DbAdabter {
    /*private no class see object from Helper
    * static to create only one object
    * Helper the inner class name
    */
    private static Helper dpHelper;
    // constructor to create object from database
    public DbAdabter(Context context){
        if(dpHelper==null){
            dpHelper=new Helper(context);
        }
    }
    //create methods
    //1.insert method
    public long insert(RequestData requestData){
        //SQLiteDatabase db object to use getWritableDatabase() method to write in database
        SQLiteDatabase dp=dpHelper.getWritableDatabase();
        //put data from  class to table
        ContentValues cv=new ContentValues();

        cv.put(Helper.requestType,requestData.getRequestType());
        cv.put(Helper.url,requestData.getUrl());
        cv.put(Helper.body,requestData.getBody());

        //insert table name , null cloumn in array,ContentValues object that get data
        long id=dp.insert(Helper.TABLE_NAME,null,cv);

        //return id next row if return -1 it mean not succues
        return id;
    }

    //2.get Method
    public RequestData getdata(){
        //SQLiteDatabase db object to use getReadableDatabase() method to read from database
        SQLiteDatabase db=dpHelper.getReadableDatabase();
        //column index 0 is first column which in our case is id
        int i=0;
        //cursor
        Cursor c;
        //array of string have all coloumn name from my schema in helper that i need data from it
        String[] columns={Helper.id, Helper.requestType, Helper.url};
        //SQLiteDatabase db get data table name ,columns ,select specific data,sort,group
        c=db.query(Helper.TABLE_NAME,columns,null,null,null,null,null,null);
        c.moveToLast();
        if(c!=null){

        String requestType = c.getString(1);
        String url = c.getString(2);
        String body = c.getString(3);
        return new RequestData(requestType,url,body);}
        return null;
    }

    //3.delete Method
    public void Delete(){
        SQLiteDatabase db=dpHelper.getWritableDatabase();
        db.execSQL("delete from " + Helper.TABLE_NAME );
    }








    private static class Helper extends SQLiteOpenHelper {
        //DB Name
        public static final String DATABASE_NAME = "MYDB";
        //DB Version
        public static final int DATABASE_VERSION = 1;
        //Table Name
        public static final String TABLE_NAME = "Demo";
        //columns Name
        public static final String id = "ID";
        public static final String requestType = "RequestType";
        public static final String url = "URL";
        public static final String body = "body";
        //create Table String
        public static final String CREATE_TABLE = "Create TABLE " + TABLE_NAME +
                " (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + requestType + " VARCHAR(25), " + url + " VARCHAR(255), " + body + " VARCHAR(250)); ";

        /*constructor
         * take Context as parameter
         * super call parent SQLiteOpenHelper take 1.context 2.DATABASE_NAME 3. cursor rather than resultset put it null 4.DATABASE_VERSION
         * */
        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        ;

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
