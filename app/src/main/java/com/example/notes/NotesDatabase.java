package com.example.notes;

//создаем БД

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)     //передаем все таблицы, версиюБД
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase database;     //моздаем БД в класее
    private static final String DB_NAME = "notes2.db";

    private static final Object LOCK = new Object();    //объект СИНХРОНИЗАЦИИ замок - на случай попытки создать несколько БД из разных потоков !

    public static NotesDatabase getInstance(Context context){     //использование паттерна SINGLETON для проверки что БД создается в одном экз.
        synchronized (LOCK) {         //замок на время создания БД
            if (database == null) {
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME)
                        .allowMainThreadQueries()       //возможность работы с БД в главном потоке - ТОЛЬКО ДЛЯ ТЕСТИРОВАНИЯ БД!!!
                        .build();
            }
        } //end of synchronized
        return database;   //всегда возвращается один и тот же объект
    }

        // абсирактный метод - получаем объект интерфеса NotesDao
    public abstract NotesDao notesDao ();

}


