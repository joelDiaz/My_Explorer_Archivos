package com.example.isabel.myexplorer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AndroidDeveloper01 on 16/3/16.
 */
public class ManagmentSqlite extends SQLiteOpenHelper {

    public ManagmentSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    String sqlCreate = "CREATE TABLE User (ID Integer PRIMARY KEY AUTOINCREMENT, nombre TEXT, email TEXT,password varchar(50) NOT NULL)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Elimina la base de datos

        db.execSQL("DROP TABLE IF EXISTS REGISTRO");


        // Crea la base de datos nuevamente
        db.execSQL(sqlCreate);



    }

}
