package com.upsoul.jittrnotes.data;

import android.content.Context;

import com.upsoul.jittrnotes.data.models.Note;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Note.class}, version = 1)
public abstract class Notes_Database extends RoomDatabase {
    private static volatile Notes_Database instance;
    public abstract Notes_DAO notesDao();

    public static Notes_Database getInstance(Context context) {
        if (instance == null) {
            synchronized (Notes_Database.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), Notes_Database.class, "NOTES_DATABASE").build();
                }
            }
        }
        return instance;
    }
}
