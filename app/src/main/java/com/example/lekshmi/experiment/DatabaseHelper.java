package com.example.lekshmi.experiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by AppsTeam on 3/15/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "smsDB.db";
    public static final String TABLE_NAME = "PumpStatus_tb";
    public static final String SENDER= "Sender";
    public static final String DATE= "Date";
    public static final String CONTENT= "Content";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" + SENDER + " text primary key," + DATE + " text," + CONTENT + " text)");

    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String sender, String date, String content) {

        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("insert into " +PLAYERDETAILS_TABLE_NAME+ "(" +PLAYERDETAILS_COLUMN_NAME+ "," +PLAYERDETAILS_COLUMN_PIN+ "," +PLAYERDETAILS_COLUMN_GENDER+ "," +PLAYERDETAILS_COLUMN_HIGHSCORE+ ")values("+name+","+Pin+","+gender+","+highscore+")");
        ContentValues contentValues = new ContentValues();
        //String highscore = "0";
        contentValues.put(SENDER, sender);
        contentValues.put(DATE, date);
        contentValues.put(CONTENT, content);

        db.insert(TABLE_NAME, null, contentValues);

        return true;
    }

    public Cursor getData(String sender) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + SENDER + " = " + "'" + sender, null);
        return res;
    }




    public int numOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    /*
    public boolean updatePlayerDetails(String name, String Playerscore, String playerIdentity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(PLAYERDETAILS_COLUMN_NAME, name);
        if (playerIdentity.equals("2")) {
            contentValues.put(PLAYERDETAILS_COLUMN_HIGHSCORE_2PLAYER, Playerscore);
            db.update(PLAYERDETAILS_TABLE_NAME, contentValues, "" + PLAYERDETAILS_COLUMN_NAME + "=?", new String[]{name});
        } else if (playerIdentity.equals("5")) {
            contentValues.put(PLAYERDETAILS_COLUMN_HIGHSCORE_5PLAYER, Playerscore);
            db.update(PLAYERDETAILS_TABLE_NAME, contentValues, "" + PLAYERDETAILS_COLUMN_NAME + "=?", new String[]{name});
        }

        return true;
    }


    public Integer deleteHighScore(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PLAYERDETAILS_TABLE_NAME, "" + PLAYERDETAILS_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
    }


    public ArrayList<String> getAllHighScore() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + PLAYERDETAILS_COLUMN_ID + " from " + PLAYERDETAILS_TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex("" + PLAYERDETAILS_COLUMN_ID)));
            res.moveToNext();

        }
        return arrayList;
    }
    */
}

