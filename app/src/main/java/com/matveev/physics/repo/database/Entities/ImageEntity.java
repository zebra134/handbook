package com.matveev.physics.repo.database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "img")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    private long img_id;
    private long text_id;
    private String img_path;

    public ImageEntity(long img_id, long text_id, String img_path) {
        this.img_id = img_id;
        this.text_id = text_id;
        this.img_path = img_path;
    }

    public long getImg_id() {
        return img_id;
    }

    public void setImg_id(long img_id) {
        this.img_id = img_id;
    }

    public long getText_id() {
        return text_id;
    }

    public void setText_id(long text_id) {
        this.text_id = text_id;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
