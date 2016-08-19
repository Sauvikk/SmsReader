package com.example.souvik.pfmUi;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.souvik.pfmUi.model.DatabaseHandler;


public class PFM extends Application {

    public static DatabaseHandler databaseHandler;
    public static SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHandler = new DatabaseHandler(this);
        db = databaseHandler.getWritableDatabase();
    }

}
