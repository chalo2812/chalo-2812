package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDao extends SQLiteOpenHelper {

    public BaseDao(Context context, String name, CursorFactory factory, int version) {
	// TODO: por default es super(context, name, factory, version);
	super(context, name, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
