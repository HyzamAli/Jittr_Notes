package com.upsoul.jittrnotes.data;

import com.upsoul.jittrnotes.data.models.Note;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface Notes_DAO {

    @Insert
    void addNote(Note note);

    @Query("SELECT * FROM NOTES_TABLE ORDER BY TIME_STAMP DESC")
    List<Note> getAllNotes();

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
