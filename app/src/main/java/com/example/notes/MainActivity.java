package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;

//    private NotesDatabase database;     доступ к БД уже не нужен
    private MainViewModel viewModel;   //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
             actionBar.hide();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);    //получаем viewModel
  //      database = NotesDatabase.getInstance(this);   //получаем базу данных
        getData();   //Нужно запусть ТОЛЬКО ОДИН РАЗ в onCreate
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

         adapter = new NotesAdapter(notes); //создаем адаптер и передаем еме Заметки
        //adapter = new NotesAdapter();
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));  // располагать элементы по вертикали последовательно, могут быть варианты
        recyclerViewNotes.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {     //устанавливаем слушателя, передаем объект нашего созданного слушателя ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView
            @Override
            public void onNoteClick(int position) {     // здесь основной код реакции на простое нажатие
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {         // здесь основной код реакции на простое нажатие
                remove(position);             //удаление элемента
            }
        });

        ItemTouchHelper itemTouch = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {   //вариант удаления СВАЙПОМ в сторону
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());    //удаление в свайпе
            }
        });
        itemTouch.attachToRecyclerView(recyclerViewNotes);   //применяем на конкретном RecycleView

    }

    private void remove(int position) {   // удаление данных из БД
       // Note note = notes.get(position);     //получаем экземпляр записи
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);     //действуем через viewModel
 //       database.notesDao().deleteNote(note);  //удаляем запись из БД
      // getData();                              //считываем данные снова      //ТЕПЕРЬ ЭТИ МЕТОДЫ НЕ НУЖНЫ - notes и RecicleView обновляются автоматически после изм. в БД !!! т.к. LIVEDATA
        //   adapter.notifyDataSetChanged();         //применить на адапторе

    }


    public void onClickAdNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);

    }

    private void getData() {                     //метод чтение из БД  - запускаем только один раз в onCreate mainActivity
        // List<Note> notesFromDB = database.notesDao().getAllNotes();   //у БД через метод notesDao() возвращающий интерфейс NotesDao вызваем метод чтения всех данных интерфейса
                                                                    //действуем через viewModel
        LiveData<List<Note>> notesFromDB = viewModel.getNotes();   //   notesFromDB - теперь это объект observerble (просматриваемый) - контроль изменений со стороны БД
        notesFromDB.observe(this, new Observer<List<Note>>() {          //для использования изменений
            @Override
            public void onChanged(List<Note> notesFromLiveData) {              //в параметрах Лист записей, которые изменились
               // notes.clear();                        ПОСЛЕ МОДЕРНИЗАЦИИ АДАПТЕРА исключаем !!!
              //  notes.addAll(notesFromLiveData);
              //  adapter.notifyDataSetChanged();
                adapter.setNotes(notesFromLiveData);   //заменили на одну строку ЗДЕСЬ ТЕПЕРЬ ПЕРЕДАЮТСЯ ВСЕ ДАННЫЕ В АДАПТЕР
            }
        });

    }


}