package com.upsoul.jittrnotes.data.repositories;

import android.app.Application;

import com.upsoul.jittrnotes.data.Notes_DAO;
import com.upsoul.jittrnotes.data.Notes_Database;
import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.models.Response;
import com.upsoul.jittrnotes.data.models.STATUS;

import java.util.List;

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

    //------ INSERT NEW NOTE ------
    public LiveData<Response<Integer>> insertNewNote(Note note) {
        MutableLiveData<Response<Integer>> data = new MutableLiveData<>();
        database.getQueryExecutor().execute(() -> {
            try {
                notesDao.addNote(note);
                data.postValue(new Response<>(STATUS.SUCCESS));
            } catch (Exception e) {
                data.postValue(new Response<>(STATUS.FAIL));
            }
        });
        return data;
    }

    //------ GET ALL NOTE ------
    public LiveData<Response<List<Note>>> getAllNotes() {
        MutableLiveData<Response<List<Note>>> data = new MutableLiveData<>();
        database.getQueryExecutor().execute(() -> {
            try {
                List<Note> notes = notesDao.getAllNotes();
                data.postValue(new Response<>(STATUS.SUCCESS, notes));
            } catch (Exception e) {
                data.postValue(new Response<>(STATUS.FAIL));
            }
        });
        return data;
    }
}
