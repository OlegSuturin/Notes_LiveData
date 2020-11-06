package com.example.notes;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//КЛАСС DBHelper - помощник при работе с БД
public class NotesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";    //имя БД
    private static final int DB_VERSION = 2;   //версия БД


    public NotesDBHelper(@Nullable Context context) {    // удалили праметры, оставили context
        // super(context, name, factory, version);
        super(context, DB_NAME, null, DB_VERSION);  //преобразованы апараметры по умолчанию
    }


    @Override                                       //вызывается при создании БД
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);    //создаем нашу таблицу
    }

    @Override                                               //вызывается при обновлении БД
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);    //удаляем старую таблицу
        onCreate(db); //пересоздаем таблицу

    }
}
