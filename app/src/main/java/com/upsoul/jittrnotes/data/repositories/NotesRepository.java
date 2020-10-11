package com.upsoul.jittrnotes.data.repositories;

import android.app.Application;

import com.upsoul.jittrnotes.data.Notes_DAO;
import com.upsoul.jittrnotes.data.Notes_Database;
import com.upsoul.jittrnotes.data.models.Note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NotesRepository {
    private static NotesRepository instance;
    private Notes_Database database;
    private Notes_DAO notesDao;

    public NotesRepository(Application application) {
        database = Notes_Database.getInstance(application);
        notesDao = database.notesDao();
    }

    public static NotesRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (NotesRepository.class) {
                if (instance == null) {
                    instance = new NotesRepository(application);
                }
            }
        }
        return instance;
    }

    public LiveData<Integer> insertNewNote(Note note) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        database.getQueryExecutor().execute(() -> {
            try {
                notesDao.addNote(note);
                data.postValue(0);
            } catch (Exception e) {
                data.postValue(1);
            }
        });
        return data;
    }
}
