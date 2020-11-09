package com.example.notes;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
       private static NotesDatabase database;         //получаем БД
        private LiveData<List<Note>> notes;     //объект LiveData в котором будем хранить все записи

    public MainViewModel(@NonNull Application application) {   //конструктор с параметрами - переопределен
        super(application);
        database = NotesDatabase.getInstance(getApplication());   //получаем БД
        notes = database.notesDao().getAllNote();   // получаем все записи, будет выполняться в другом программном потоке.
    }

    public LiveData<List<Note>> getNotes() {    // добавили геттер на записи
        return notes;
    }

    public void isertNote(Note note) {      //ПЕРВЫЙ метод - вставка записи- должен исполняться в другом программном потоке
        new InsertTask().execute(note);   //анонимный запуск другого программного потока
    }
                                //создали класс для работы в другом программном потоке
    private static class InsertTask extends AsyncTask<Note, Void, Void>{    //Note - принмает первый параметр, Void - в процессе не нужны данные, Void - возвращать данные не нужно
        @Override
        protected Void doInBackground(Note... notes) {    // метод doInBackground переопределен
            if (notes != null && notes.length >0 ) {   //в этом случае вствляем данные в БД
                database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    public void deleteNote(Note note) {      //ВТОРОЙ метод - удаление записи - должен исполняться в другом программном потоке
        new DeleteTask().execute(note);   //анонимный запуск другого программного потока
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void>{    //Note - принмает первый параметр, Void - в процессе не нужны данные, Void - возвращать данные не нужно
        @Override
        protected Void doInBackground(Note... notes) {    // метод doInBackground переопределен
            if (notes != null && notes.length >0 ) {   //в этом случае вствляем данные в БД
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    public void deleteAllNotes() {      //ТРЕТИЙ метод - удаление всех записей - должен исполняться в другом программном потоке
        new DeleteAllTask().execute();   //анонимный запуск другого программного потока
    }


    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... notes) {    // метод doInBackground переопределен
                database.notesDao().deleteAllNotes();
            return null;
        }
    }

}
