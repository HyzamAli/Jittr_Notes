package com.upsoul.jittrnotes.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.repositories.NotesRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LauncherViewModel extends AndroidViewModel {
    private SharedPreferences preferences;
    private NotesRepository repository;


    public LauncherViewModel(@NonNull Application application) {
        super(application);
        repository = NotesRepository.getInstance(application);
        preferences = getApplication().getSharedPreferences("", Context.MODE_PRIVATE);
        setFirstNote();
    }


    public boolean authenticate(String pin) {
        String key = preferences.getString("PIN", null);
        if (key == null) {
            return true;
        } else {
            return pin.equals(key);
        }
    }

    public boolean authenticate() {
        String key = preferences.getString("PIN", null);
        return key == null;
    }

    private void setFirstNote() {
        if (!preferences.contains("FIRST_NOTE")) {
            repository.insertNewNote(new Note("Welcome","Hey there, welcome to Jittr Notes. Its fun, easy and secure"));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("FIRST_NOTE", true);
            editor.apply();
        }
    }

    @Override
    protected void onCleared() {
        preferences = null;
        super.onCleared();
    }
}
