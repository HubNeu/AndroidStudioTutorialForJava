package com.example.zgadywanka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "NAMES";
    public static final String TAG = "DatabaseHelper";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "SCORE";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean addData(String argName, int argScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, argName);
        contentValues.put(COL3, argScore);

        Log.d(TAG, "addedData: Adding: " + argName + " and " + argScore + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result==-1){
            return false;
        }
        return true;
    }

    public Cursor pullData(){
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return sqldb.rawQuery(query,null);
    }

    public void deleteAll(){
        this.getWritableDatabase().execSQL("delete from "+ TABLE_NAME);
    }
}
