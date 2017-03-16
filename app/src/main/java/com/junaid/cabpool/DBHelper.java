package com.junaid.cabpool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyCabs.db";
    private static final String TABLE_NAME = "cabIds";
    private static final String COLUMN_ID = "id";

    private static int DATABSE_VERSION = 20;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + TABLE_NAME +
                        "( " + COLUMN_ID +" text primary key);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertID (String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID ,ID);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();

    }


    public ArrayList<String> getIDs(){
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT* FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        return list;
    }

    public boolean findID(String ID){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{COLUMN_ID}, COLUMN_ID + "= ?",
                new String[]{ID},null,null,null,null);

        Log.d("database",cursor.getCount() + "");

        return cursor.getCount() != 0;

    }

    public void deleteID(String ID){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT* FROM " + TABLE_NAME;
        db.delete(TABLE_NAME,COLUMN_ID + "= ?",new String[]{ID});
        db.close();

    }

    public int getCount(){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT* FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        cursor.close();

        return cursor.getCount();

    }

}