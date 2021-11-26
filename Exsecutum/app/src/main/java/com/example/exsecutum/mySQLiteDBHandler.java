/*
Program Name: mySQLiteDBHandler
Programmer Names: Robert S. Zecchini
Version: 1.0
Purpose: This segment of code creates, assigns, and manages user's tasks by storing the user's
        tasks within a SQL database.
*/

package com.example.exsecutum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class mySQLiteDBHandler extends SQLiteOpenHelper {

    //Constructor class.
    public mySQLiteDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //This function overrides the creation of the database.
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //This function overrides how the database gets updated.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
