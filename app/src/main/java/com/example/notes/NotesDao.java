package com.example.notes;

// инетрфейс DAO - объект доступа к данным

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao                            //аннотация DAO
public interface NotesDao {                         //класс содержит методы для доступа к БД

    @Query("SELECT * FROM notes ORDER BY dayOfWeek ASC")    //DESC - сортировка в обратном порядке ASC - возрастания
    //List<Note> getAllNotes();          //метод вызывается при запросе к БД и загружает все данные, результат ArrayList
    LiveData<List<Note>> getAllNote();      //теперь метод возвращает объект LiveData, который содержит список записей

    @Insert
    void insertNote(Note note);             //вставл данные

    @Delete
    void deleteNote(Note note);             //удаляет данные

    @Query("DELETE FROM notes")
    void deleteAllNotes();                  //метод удаляет все данные из таблицы notes

}
