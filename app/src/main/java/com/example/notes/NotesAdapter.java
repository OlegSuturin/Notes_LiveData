package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    List<Note> notes;  // МОДЕРНИЗАЦИЯ адаптера  ИЗМЕНИЛИ на List
    private OnNoteClickListener onNoteClickListener; //создали объект нашего слушателя 2)  ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView

        //МОДЕРНИЗАЦИЯ адаптера с целью чтобы не загружать целиком все заметки каждый раз прю любом в записях БД в БД
    public void setNotes(List<Note> notes) {       //ДОБАВИЛИ СЕТТЕР НА ArrayList
        this.notes = notes;
        notifyDataSetChanged();
    }
            //МОДЕРНИЗАЦИЯ адаптера - добавили getter на актуальный список (изменяющийся)
    public List<Note> getNotes() {
        return notes;
    }

    public interface OnNoteClickListener {     //интерфес для реализации реакции на нажатия 1)  ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView
        void onNoteClick(int position);     //реакция на простое нажатие
        void onLongClick(int position);     //реакция на долгое нажатие
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;     //добавили сеттер на объект листенера 3)  ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView
    }

    public NotesAdapter(ArrayList<Note> notes) {        // конструктор адаптера
        this.notes = notes;  //инициализирум массив объектами Заметка
   }

    @NonNull
    @Override
    //берем макет note_item и передаем в виде аргумента конструктору NotesViewHolder
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);  //получаем объект из массива
        holder.textViewTitle.setText(note.getTitle());  //заполняем поля данными
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayofWeek.setText(Note.getDayAsString(note.getDayOfWeek()+1));
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        holder.textViewTitle.setBackgroundColor(colorId); // установили цвет фона заголовка в зависимости отприоритета


    }

    @Override
    public int getItemCount() {    //возвращает кол-во заметок
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayofWeek;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayofWeek = itemView.findViewById(R.id.textViewDayOfWeek);

            itemView.setOnClickListener(new View.OnClickListener() {    // передаем слушателю объекта itemView анонимный класс  4) ТЕМА: РЕАКЦИЯ НА НАЖАТИЯ в RecycleView
                @Override
                public void onClick(View v) {                            // метод вызывается при нажатии на элемент RecicleView
                    if (onNoteClickListener != null){
                        int position = getAdapterPosition();          //определяем номер позиции адаптера
                        onNoteClickListener.onNoteClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {   // передается ДОЛГОЕ нажатие
                @Override
                public boolean onLongClick(View v) {
                    if(onNoteClickListener!=null){
                        int position = getAdapterPosition();
                        onNoteClickListener.onLongClick(position);
                    }

                    return true;   //!!!!! ОБЯЗАТЕЛЬНО true, иначе сработаю оба метода
                }
            });

        }
    }
}
