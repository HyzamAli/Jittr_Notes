package com.upsoul.jittrnotes.viewmodel;

import android.app.Application;

import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.repositories.NotesRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository repository;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = NotesRepository.getInstance(application);
    }

    public LiveData<Integer> insertNewNote(Note note) {
        return repository.insertNewNote(note);
    }
}