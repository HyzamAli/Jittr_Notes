package com.upsoul.jittrnotes.data.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "NOTES_TABLE")
@SuppressWarnings({"unused", "RedundantSuppression"})
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "TITLE")
    private String title;

    @ColumnInfo(name = "DESCRIPTION")
    private String description;

    @ColumnInfo(name = "TIME_STAMP")
    private Long time_stamp;

    @ColumnInfo(name = "DATE")
    private String date;

    @ColumnInfo(name = "PRIORITY")
    private int priority;

    @ColumnInfo(name = "COLOR")
    private int color;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime_stamp(Long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getTime_stamp() {
        return time_stamp;
    }

    public String getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Note(int id, String title, String description, Long time_stamp, String date, int priority, int color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time_stamp = time_stamp;
        this.date = date;
        this.priority = priority;
        this.color = color;
    }

    @Ignore
    public Note(int color) {
        this.title = "";
        this.description = "";
        this.date = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(Calendar.getInstance().getTime());
        this.priority = 0;
        this.color = color;
        time_stamp = System.currentTimeMillis();
    }

    @Ignore
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(Calendar.getInstance().getTime());
        this.priority = 0;
        this.color = 1;
        time_stamp = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return  priority == note.priority &&
                Objects.equals(title, note.title) &&
                Objects.equals(description, note.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, priority);
    }
}
