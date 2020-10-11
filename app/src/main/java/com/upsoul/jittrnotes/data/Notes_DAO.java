package com.upsoul.jittrnotes.data;

import com.upsoul.jittrnotes.data.models.Note;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface Notes_DAO {

    @Insert
    void addNote(Note note);
}
