package com.matveev.physics.repo.database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "text")
public class TextEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String text;
    private String section;

    public TextEntity(long id, String text, String section) {
        this.id = id;
        this.text = text;
        this.section = section;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
