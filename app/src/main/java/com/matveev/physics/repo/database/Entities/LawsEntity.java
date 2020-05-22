package com.matveev.physics.repo.database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "laws")
public class LawsEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String section;
    private String title;
    private String text;
    private String img_path;

    public LawsEntity(long id, String section, String title, String text, String img_path) {
        this.id = id;
        this.section = section;
        this.title = title;
        this.text = text;
        this.img_path = img_path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
