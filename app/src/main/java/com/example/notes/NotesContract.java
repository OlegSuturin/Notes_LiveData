package com.example.notes;

import android.provider.BaseColumns;
                                            //  КЛАСС  CONTRACT ХРАНИТ ВСЮ ИНФ. О ТАБЛИЦЕ И КОММАНДЫ СОЗДАНИЯ УДАЛЕНИЯ
public class NotesContract {
    public static final class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";        //имя таблицы

        public static final String COLUMN_TITLE = "title";       //названия полей - заголовков колонок
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEAK = "day_of_weak";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String TYPE_TEXT = "TEXT";           //названия  типов данных
        public static final String TYPE_INTEGER = "INTEGER";
                                                                    //комманда на создание таблицы
        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " " + TYPE_TEXT + ", " +
                COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " +
                COLUMN_DAY_OF_WEAK + " " + TYPE_INTEGER + ", " +
                COLUMN_PRIORITY + " " + TYPE_INTEGER+ ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " +TABLE_NAME;  //комманда на удаление таблицы
    }
}
