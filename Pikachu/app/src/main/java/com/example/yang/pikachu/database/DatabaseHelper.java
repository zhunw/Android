package com.example.yang.pikachu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Yang on 2018/7/1.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //create the database
    //(id, task, addition, date, time, count)
    private final String TABLE_NAME_SQL=
            "create table alarm_task(_id integer primary key autoincrement,"+
            "task varchar(100),"+
            "addition varchar(100),"+
            "date1 varchar(30),"+
            "time varchar(30),"+
            "count integer)";

    public DatabaseHelper(Context context, String name,
                            CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(TABLE_NAME_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
