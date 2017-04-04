package com.example.chen.exp8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chen on 2016/11/20.
 */

public class myDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "BirthdayInfo";
    public myDB(Context context, String name, SQLiteDatabase.CursorFactory factory,
                int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                +TABLE_NAME
                +" (_id INTEGER PRIMARY KEY, name Text, birthday TEXT, gift TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
