package com.upsoul.jittrnotes.viewmodel;

import android.app.Application;

import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.models.Response;
import com.upsoul.jittrnotes.data.repositories.NotesRepository;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import static java.util.Calendar.HOUR_OF_DAY;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository repository;
    private Note currentNote;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = NotesRepository.getInstance(application);
    }

    public String greetingsText() {
        int currentHour = Calendar.getInstance().get(HOUR_OF_DAY);
        if (6<= currentHour && currentHour < 12) {
            return "Good\nMorning";
        } else if (12<= currentHour && currentHour < 15) {
            return "Good\nAfternoon";
        } else if (15<= currentHour && currentHour < 19) {
            return "Good\nEvening";
        } else {
            return "Good\nNight";
        }
    }

    public Note getCurrentNote() {
        return currentNote;
    }

    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }

    public LiveData<Response<Integer>> insertNewNote(Note note) { return repository.insertNewNote(note); }

    //TODO: check Live data response
    public LiveData<Response<Integer>> updateNote(Note note) { return repository.updateNote(note); }

    public LiveData<Response<Integer>> deleteNote(Note note) { return repository.deleteNote(note); }

    public LiveData<Response<List<Note>>> getAllNotes() {
        return repository.getAllNotes();
    }
}
