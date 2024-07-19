package com.example.shopSaver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "listas.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla para cada tienda
        db.execSQL("CREATE TABLE IF NOT EXISTS Dia (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, is_activo INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Bonarea (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, is_activo INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Eroski (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, is_activo INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Mercadona (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, is_activo INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejo de la actualización de la base de datos
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Mercadona (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, is_activo INTEGER)");
        }
        // Agregar más condiciones para manejar versiones posteriores aquí si es necesario
    }

}

