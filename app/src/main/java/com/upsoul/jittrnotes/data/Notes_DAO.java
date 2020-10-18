package com.upsoul.jittrnotes.data;

import com.upsoul.jittrnotes.data.models.Note;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface Notes_DAO {

    @Insert
    void addNote(Note note);

    @Query("SELECT * FROM NOTES_TABLE ORDER BY TIME_STAMP DESC")
    List<Note> getAllNotes();
}
