package com.example.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")            //для использования объекта с БД + конструктор + все геттеры и сеттеры

public class Note {
    @PrimaryKey(autoGenerate = true)    //прописываем primary key
    private int id;                     //id генерируется автоматически
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Note(int id, String title, String description, int dayOfWeek, int priority) {   //основной конструктор, инициализирует ВСЕ поля
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }
    @Ignore                                                                        //т.к. должен быть только один основной конструктор
    public Note(String title, String description, int dayOfWeek, int priority) {   //второй конструктор, где инициализируем все поля, кроме id, т.к. он генерируется атоматически
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    public static String getDayAsString(int position){
        switch (position){
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
            default:
                return "Воскресенье";
        }
    }
}
